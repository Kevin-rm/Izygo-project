package mg.motus.izygo.service;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.repository.ReservationRepository;
import mg.motus.izygo.repository.ReservationSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationSeatRepository reservationSeatRepository;
    private TicketService ticketService;
    

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,ReservationSeatRepository reservationSeatRepository, TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.ticketService = ticketService;
    }

    public List<ReservationDTO> createReservation(Long userId, Long busId,int startStopId,int endStopId, List<Integer> seatIds) {
        Reservation reservation = Reservation.builder()
        .busId(busId)
        .userId(userId)
        .dateTime(LocalDateTime.now())
        .departureStopId(startStopId)
        .arrivalStopId(endStopId)
        .build();
        reservation = reservationRepository.save(reservation);

        for (Integer seatId : seatIds) {
            ReservationSeat reservationSeat = ReservationSeat.builder()
            .reservationId(reservation.getId())
            .seatId(seatId.shortValue())
            .build();
            reservationSeatRepository.save(reservationSeat);
        }

        List<ReservationDTO> reservationDTOs = reservationSeatRepository.findReservationsById(reservation.getId());
        for (ReservationDTO reservationDTO : reservationDTOs) {
            ticketService.addTicketInfo(reservationDTO);
        }


        return reservationDTOs;
    }

    public List<Integer> getReservedSeats(Long busId,Long departureStopId) {
        return reservationRepository.findReservedSeatsByBusId(busId,departureStopId);
    }
}
