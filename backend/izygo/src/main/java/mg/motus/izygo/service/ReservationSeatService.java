package mg.motus.izygo.service;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.repository.ReservationSeatRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationSeatService {
    private ReservationSeatRepository reservationSeatRepository;

    @Autowired
    public ReservationSeatService(ReservationSeatRepository reservationSeatRepository) {
        this.reservationSeatRepository = reservationSeatRepository;
    }

    public List<ReservationDTO> getReservationById(Long id) {
        return reservationSeatRepository.findReservationsById(id);
    }
}
