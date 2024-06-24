package mg.motus.izygo.service;

import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.User;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.repository.ReservationRepository;
import mg.motus.izygo.repository.ReservationSeatRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationSeatRepository reservationSeatRepository;
    

    @Autowired
    public ReservationService(ReservationRepository reservationRepository,ReservationSeatRepository reservationSeatRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
    }

    public Reservation createReservation(Long userId, Long busId,int startStopId,int endStopId, List<Integer> seatIds) {
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
        return reservation;
    }

    // public List<ReservationSeat> getActiveReservations() {
    //     return reservationSeatRepository.findByIsActiveTrue();
    // }

    public List<Integer> getReservedSeats(Long busId,Long departureStopId) {
        return reservationRepository.findReservedSeatsByBusId(busId,departureStopId);
    }
}
