package mg.motus.izygo.controller;

import org.springframework.web.bind.annotation.RestController;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.service.ReservationSeatService;
import mg.motus.izygo.utilities.Hashing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class ScanTicketController {
    
    private ReservationSeatService reservationSeatService;
    
    public ScanTicketController(ReservationSeatService reservationSeatService) {
        this.reservationSeatService = reservationSeatService;
    }

    @GetMapping("scanTicket/{hashedId}")
    public String UpdateTicketState(@PathVariable String hashedId) {
        String reservationSeadId = Hashing.decodeBase64(hashedId);
        reservationSeatService.checkReservationState(Long.parseLong(reservationSeadId));
        return new String("Vita oh");
    }

    @GetMapping("Ticket/{id}")
    public ResponseEntity<Map<String, String>> getImgReservation(@PathVariable String id){
        System.out.println("Tonga ato"+id);
        Path imageDirectory = Paths.get("src/main/resources/qr_ressources/Ticket");
        List<ReservationDTO> listDTO = reservationSeatService.getReservationById(2L);
         Map<String, String> imageMap = new HashMap<>();
        for (ReservationDTO reservationDTO : listDTO) {
            String nameimg = Hashing.encodeBase64(reservationDTO.reservationSeatId().toString()) + ".png"; // Ajoutez l'extension appropriée
            Path imagePath = imageDirectory.resolve(nameimg);

            if (Files.exists(imagePath)) {
                try {
                    byte[] imageBytes = Files.readAllBytes(imagePath);
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    imageMap.put(nameimg, base64Image);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseEntity<>(imageMap, HttpStatus.OK);
    }
    
}
