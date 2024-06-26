package mg.motus.izygo.controller;
import mg.motus.izygo.dto.BusArrivalDTO;
import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.ResearchRepository;
import mg.motus.izygo.service.ReservationService;
import mg.motus.izygo.service.UserService;
import mg.motus.izygo.utilities.Hashing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/book")
public class ReservationController {

    private final ReservationService reservationService;
    private final ResearchRepository researchRepository;


    @Autowired
    public ReservationController(ReservationService reservationService,ResearchRepository researchRepository) {
        this.reservationService = reservationService;
        this.researchRepository=researchRepository;
    }

    @PostMapping("/bookBus")
    public ResponseEntity<?> bookBus(@RequestBody Map<String, Object> reservationSeatData) {
        System.out.println(">>"+reservationSeatData+"<<");
        try {
            Long userId = ((Integer) reservationSeatData.get("userId")).longValue();
            Long busId = ((Integer) reservationSeatData.get("busId")).longValue();
            int startStopId = (Integer) reservationSeatData.get("startStop");
            int endStopId = (Integer) reservationSeatData.get("endStop");
            List<Integer> seatIds = (List<Integer>) reservationSeatData.get("seatIds");
            Integer seatPrice = (Integer) reservationSeatData.get("seatPrice");

            // Ajout de la reservation dans la base de données et génération du ticket
            List<ReservationDTO> listDTO = reservationService.createReservation(userId, busId, startStopId, endStopId, seatIds,seatPrice.doubleValue());

            Path imageDirectory = Paths.get("src/main/resources/qr_ressources/Ticket");

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
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Reservation failed");
            errorResponse.put("message", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/getReserved")
    public List<Integer> reservedSeats(@RequestBody Map<String, Object> BusAndArrival){
        Long BusId = ((Integer) BusAndArrival.get("busId")).longValue();
        Long arrival = ((Integer) BusAndArrival.get("arrival")).longValue();

        return reservationService.getReservedSeats(BusId,arrival);
    }

    @PostMapping("/getBus")
    public BusArrivalDTO findBus(@RequestBody Map<String,Object> data){
        int departureStopId=(Integer)data.get("departureStopId");
        Timestamp timestamp1 = Timestamp.valueOf((String) data.get("dateTime1"));
        Timestamp timestamp2 = Timestamp.valueOf((String) data.get("dateTime2"));

        return researchRepository.findFutureArrivingBuses(departureStopId, timestamp1, timestamp2, "1 minute").get(0);
    }

    @PostMapping("nbarret")
    public Integer getStopCount(@RequestBody Map<String,Object> data) {
        Integer departureStopId = (Integer) data.get("departureStop");
        Integer arrivalStopId = (Integer) data.get("arrivalStop");
        return reservationService.getStopCount(departureStopId, arrivalStopId).get(0);
    }
    

    // SELECT * FROM find_future_arriving_buses(14, '2024-06-26 08:00:00'::timestamp, '2024-06-26 08:20:00'::timestamp, '1 minutes');


}
