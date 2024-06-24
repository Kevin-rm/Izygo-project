package mg.motus.izygo.service;

import mg.motus.izygo.repository.ReservationSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationSeatService {
    private ReservationSeatRepository reservationSeatRepository;

    @Autowired
    public ReservationSeatService(ReservationSeatRepository reservationSeatRepository) {
        this.reservationSeatRepository = reservationSeatRepository;
    }

    
}
