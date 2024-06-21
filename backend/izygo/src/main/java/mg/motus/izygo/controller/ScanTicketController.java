package mg.motus.izygo.controller;

import org.springframework.web.bind.annotation.RestController;

import mg.motus.izygo.service.ReservationSeatService;
import mg.motus.izygo.utilities.Hashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ScanTicketController {
    
    private ReservationSeatService reservationSeatService;
    
    public ScanTicketController(ReservationSeatService reservationSeatService) {
        this.reservationSeatService = reservationSeatService;
    }

    @GetMapping("scanTicket/{hashedId}")
    public String getMethodName(@PathVariable String hashedId) {
        String reservationSeadId = Hashing.decodeBase64(hashedId);
        reservationSeatService.checkReservationState(Long.parseLong(reservationSeadId));
        return new String("Vita oh");
    }
    
}
