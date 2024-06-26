package mg.motus.izygo.service;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.repository.ReservationSeatRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReservationSeatService {
    private ReservationSeatRepository reservationSeatRepository;

    public ReservationSeatService(ReservationSeatRepository reservationSeatRepository) {
        this.reservationSeatRepository = reservationSeatRepository;
    }

    public List<ReservationDTO> getReservationById(Long id) {
        return reservationSeatRepository.findReservationsById(id);
    }

    public void checkReservationState(Long reservationSeatId) {
        ReservationSeat reservationSeat = reservationSeatRepository.findById(reservationSeatId).get();
        if (!reservationSeat.getIsActive()) {
            System.out.println("Aza tady ho fetsy bro");
            return;
        }
        if (reservationSeat.getIsActive() && reservationSeat.getOnBus()) {
            reservationSeat.setOnBus(false);
            reservationSeat.setIsActive(false);
        } else if (reservationSeat.getIsActive() && !reservationSeat.getOnBus()) {
            reservationSeat.setOnBus(true);
        }
        reservationSeatRepository.save(reservationSeat);
    }

    public void ticketCancelled(Long reservationSeatId){
        ReservationSeat reservationSeat = reservationSeatRepository.findById(reservationSeatId).get();
        reservationSeat.setIsActive(false);
        reservationSeatRepository.save(reservationSeat);
    }
}
