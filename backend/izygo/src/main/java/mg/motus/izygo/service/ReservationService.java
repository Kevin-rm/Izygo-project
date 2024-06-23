package mg.motus.izygo.service;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.repository.ReservationRepository;
import mg.motus.izygo.repository.ReservationSeatRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private ReservationRepository reservationRepository;
    private ReservationSeatRepository reservationSeatRepository;
    private TicketService ticketService;

    public ReservationService(ReservationRepository reservationRepository,ReservationSeatRepository reservationSeatRepository,TicketService ticketService) {
        this.reservationRepository = reservationRepository;
        this.reservationSeatRepository = reservationSeatRepository;
        this.ticketService = ticketService;
    }

    public List<ReservationDTO> createReservation(Long userId, Long busId,int departureStopId,int arrivalStopId, List<Short> seatIds) {
        Reservation reservation = Reservation.builder()
        .busId(busId)
        .userId(userId)
        .dateTime(LocalDateTime.now())
        .arrivalStopId(departureStopId)
        .arrivalStopId(arrivalStopId)
        .build();
        reservation = reservationRepository.save(reservation);
        

        for (Short seatId : seatIds) {
            ReservationSeat reservationSeat = ReservationSeat.builder()
            .reservationId(reservation.getId())
            .seatId(seatId)
            .build();
            reservationSeatRepository.save(reservationSeat);
        }
        
        List<ReservationDTO> reservationDTOs = reservationSeatRepository.findReservationsById(reservation.getId());
        for (ReservationDTO reservationDTO : reservationDTOs) {
            ticketService.addTicketInfo(reservationDTO);
        }
        return reservationDTOs;
    }


}
