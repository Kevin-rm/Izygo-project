package mg.motus.izygo.service;

import mg.motus.izygo.dto.NotificationParamsDTO;
import mg.motus.izygo.model.*;
import mg.motus.izygo.repository.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class CancellationService {
    private CancellationRepository cancellationRepository;
    private ReservationRepository reservationRepository;
    private ReservationSeatRepository reservationSeatRepository;
    private NotificationRepository notificationRepository;
    private NotificationService notificationService;
    private final JdbcTemplate jdbcTemplate;

    public CancellationService(CancellationRepository cancellationRepository, 
                               ReservationRepository reservationRepository, 
                               ReservationSeatRepository reservationSeatRepository,
                               NotificationRepository notificationRepository,
                               NotificationService notificationService,
                               JdbcTemplate jdbcTemplate) {
        this.cancellationRepository = cancellationRepository;
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cancellation cancelReservation(Long reservationSeatId) {

        Cancellation cancellation = Cancellation.builder()
        .reservationSeatId(reservationSeatId)
        .build();
        cancellation = cancellationRepository.save(cancellation);
        // Calculate refund date (e.g., 5 days after cancellation)
        // LocalDate refundDate = LocalDate.now().plusDays(5);
        return cancellation;
    }

    /*
     * Creating the initial parameters to send a notification:
        * the kiosk/employee at the departure stop of the Reservation,
        * formatted message in French,
        * the bus where a seat was freed,
        * the free seat from the ReservationSeat,
        * the Reservation's departure stop,
        * the Reservation's arrival stop
     */
    public NotificationParamsDTO fetchFromCancellation(Cancellation c) {
        ReservationSeat rs = reservationSeatRepository.findById(c.getReservationSeatId()).get();
        Reservation r = reservationRepository.findById(rs.getReservationId()).get();

        // the bus where there is a free seat
        Long busToFollowId = r.getBusId();
        
        Short seatId = rs.getSeatId();

        Integer departureStop = r.getDepartureStop();

        Integer arrivalStop = r.getArrivalStop();

        // fetching the kiosk assigned to the departure stop
        Long userId = jdbcTemplate.query("SELECT employee_id FROM line_stop WHERE stop_id = ?", new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return null;
            }
        }, new Object[]{departureStop});


        String seatLabel = jdbcTemplate.query("SELECT label FROM seat WHERE id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException {
                if(rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        }, new Object[]{seatId});

        String lineLabel = jdbcTemplate.query("SELECT line.label FROM line JOIN bus ON line.id = bus.line_id WHERE bus.id = ?", new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException {
                if(rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        }, new Object[]{busToFollowId});

        String message = String.format("Le siège %s s\'est libéré pour le bus %s avant votre réservation. Souhaitez-vous transférer votre réservation?", seatLabel, lineLabel); 

        return new NotificationParamsDTO(userId, message, busToFollowId, seatId, departureStop, arrivalStop);
    }

    public void sendNotification(Cancellation c, NotificationParamsDTO paramModel) throws Exception {
        if(paramModel == null) {
            // must set the initial parameters
            paramModel = fetchFromCancellation(c);
        }

        // call to the insertNotification function
        Long notificationId = this.notificationService.insertNotification(paramModel);

        // call to the asynchronous function that checks the reaction values
        // the (long) delay should still be calculated dynamically depending on the distance between departureStop and arrivalStop => waiting for function from Kevin
        CompletableFuture<Boolean> future = this.notificationService.shouldInsert(notificationId, 60000L); 

        // Fetch the Boolean result when it is done
        Boolean shouldInsert = future.get();

        // Check the result and make a recursive call if necessary
        if (shouldInsert) {
            Notification notification = this.notificationRepository.findById(notificationId).get();
            paramModel = new NotificationParamsDTO(notification.getNextUserID(), paramModel.message(), paramModel.busToFollowId(), paramModel.seatId(), paramModel.departureStop(), paramModel.arrivalStop());

            sendNotification(c, paramModel);
        }
    }
}
