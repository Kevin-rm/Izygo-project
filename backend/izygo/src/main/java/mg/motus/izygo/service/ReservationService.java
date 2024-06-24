package mg.motus.izygo.service;

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
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,ReservationSeatRepository reservationSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
    }

    public Reservation createReservation(Long userId, Long busId,int startStopId,int endStopId, List<Short> seatIds) {
        Reservation reservation = Reservation.builder()
        .busId(busId)
        .userId(userId)
        .dateTime(LocalDateTime.now())
        .build();
        reservation = reservationRepository.save(reservation);

        for (Short seatId : seatIds) {
            ReservationSeat reservationSeat = ReservationSeat.builder()
            .reservationId(reservation.getId())
            .seatId(seatId)
            .build();
            reservationSeatRepository.save(reservationSeat);
        }
        return reservation;
    }


}
