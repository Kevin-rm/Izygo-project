package mg.motus.izygo.controller;
import mg.motus.izygo.dto.BusArrivalDTO;
import mg.motus.izygo.model.Reservation;
import mg.motus.izygo.model.ReservationSeat;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.ResearchRepository;
import mg.motus.izygo.service.ReservationService;
import mg.motus.izygo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
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

    @PostMapping("/getReserved")
    public List<Integer> reservedSeats(@RequestBody Map<String, Object> BusAndArrival){
        Long BusId = ((Integer) BusAndArrival.get("busId")).longValue();
        Long arrival = ((Integer) BusAndArrival.get("arrival")).longValue();

        return reservationService.getReservedSeats(BusId,arrival);
    }

    @PostMapping("/getBus")
    public List<BusArrivalDTO> findBus(@RequestBody Map<String, Object> dateTimeReservation) {
        int departureStopId = (Integer) dateTimeReservation.get("departureStopId");

        String dateTime1Str = (String) dateTimeReservation.get("dateTime1");
        String dateTime2Str = (String) dateTimeReservation.get("dateTime2");

        // Convertir la chaîne ISO 8601 avec le suffixe 'Z' en OffsetDateTime
        OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(dateTime1Str);
        OffsetDateTime offsetDateTime2 = OffsetDateTime.parse(dateTime2Str);

        // Convertir OffsetDateTime en LocalDateTime (si nécessaire)
        LocalDateTime localDateTime1 = offsetDateTime1.toLocalDateTime();
        LocalDateTime localDateTime2 = offsetDateTime2.toLocalDateTime();

        // Convertir LocalDateTime en Timestamp
        Timestamp timestamp1 = Timestamp.valueOf(localDateTime1);
        Timestamp timestamp2 = Timestamp.valueOf(localDateTime2);

        System.out.println("D1:" + timestamp1);
        System.out.println("D2:" + timestamp2);

        // Appeler votre repository avec les Timestamp convertis
        List<BusArrivalDTO> result = researchRepository.findFutureArrivingBuses(departureStopId, timestamp1, timestamp2, "1 minute");

        // Affichage du résultat
        System.out.println("Résultat de researchRepository.findFutureArrivingBuses:");
        for (BusArrivalDTO busArrivalDTO : result) {
            System.out.println(busArrivalDTO);  // toString() de BusArrivalDTO utilisé automatiquement
        }

        return result;
    }

}
