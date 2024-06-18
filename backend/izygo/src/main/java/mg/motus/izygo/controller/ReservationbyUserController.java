package mg.motus.izygo.controller;

import mg.motus.izygo.model.ReservationbyUser;
import mg.motus.izygo.service.ReservationbyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservationsbyuser")
public class ReservationbyUserController {
    private final ReservationbyUserService reservationService;

    @Autowired
    public ReservationbyUserController(ReservationbyUserService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/user/{userId}")
    public List<ReservationbyUser> getReservationsByUserId(@PathVariable Long userId) {
        return reservationService.getReservationsByUserId(userId);
    }
}
