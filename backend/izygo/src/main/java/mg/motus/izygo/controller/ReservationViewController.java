package mg.motus.izygo.controller;

import mg.motus.izygo.model.ReservationView;
import mg.motus.izygo.service.ReservationViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationViewController {
    private final ReservationViewService reservationService;

    @Autowired
    public ReservationViewController(ReservationViewService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{userId}")
    public List<ReservationView> getReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }
}
