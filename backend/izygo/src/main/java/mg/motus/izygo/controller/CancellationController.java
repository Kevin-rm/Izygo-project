package mg.motus.izygo.controller;

import mg.motus.izygo.model.Cancellation;
import mg.motus.izygo.service.CancellationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cancellations")
public class CancellationController {

    private final CancellationService cancellationService;

    @Autowired
    public CancellationController(CancellationService cancellationService) {
        this.cancellationService = cancellationService;
    }

    @PostMapping("/cancel")
    public ResponseEntity<Cancellation> cancelReservation(@RequestParam Long reservationSeatId) {
        Cancellation cancellation = cancellationService.cancelReservation(reservationSeatId);
        return ResponseEntity.ok(cancellation);
    }
}
