package mg.motus.izygo.controller;

import mg.motus.izygo.dto.ProfileReservationSeatDTO;
import mg.motus.izygo.model.Cancellation;
import mg.motus.izygo.service.CancellationService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CancellationController {

    private final CancellationService cancellationService;

    @Autowired
    public CancellationController(CancellationService cancellationService) {
        this.cancellationService = cancellationService;
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelReservation(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("reservationSeatId");
            Long reservationSeatId = Long.parseLong(id);
            Cancellation cancellation = cancellationService.cancelReservation(reservationSeatId);
            return new ResponseEntity<>(cancellation, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
