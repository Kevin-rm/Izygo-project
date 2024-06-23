package mg.motus.izygo.controller;
import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.ReservationService;
import mg.motus.izygo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/book")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/bookBus")
    public ResponseEntity<?> bookBus(@RequestBody Map<String, Object> reservationSeatData) {
        System.out.println(reservationSeatData);
        try {
            // Long userId = ((Integer) reservationSeatData.get("userId")).longValue();
            Long busId = ((Integer) reservationSeatData.get("busId")).longValue();
            int startStopId = (Integer) reservationSeatData.get("startStopId");
            int endStopId = (Integer) reservationSeatData.get("endStopId");
            List<Integer> seatIds = (List<Integer>) reservationSeatData.get("seatIds");

            Reservation reservation = reservationService.createReservation(1L, busId, startStopId, endStopId, seatIds);
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            System.out.println(e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Reservation failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


}
