package mg.motus.izygo.controller;

import mg.motus.izygo.dto.ProfileReservationDTO;
import mg.motus.izygo.dto.ProfileReservationSeatDTO;
import mg.motus.izygo.service.ProfileUserService;
import mg.motus.izygo.service.ReservationSeatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProfileUserController {

    private final ProfileUserService profileUserService;
    private final ReservationSeatService reservationSeatService;

    @Autowired
    public ProfileUserController(ProfileUserService profileUserService,ReservationSeatService reservationSeatService) {
        this.profileUserService = profileUserService;
        this.reservationSeatService = reservationSeatService;
    }

    @PostMapping("/Profil/reservation")
    public ResponseEntity<?> getReservationByUser(@RequestBody Map<String, Integer> request) {
        try {
            Integer id = request.get("id");
            List<ProfileReservationDTO> profileReservationDTOs = profileUserService.getReservationsByUserId(id.longValue());
            return new ResponseEntity<>(profileReservationDTOs, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Profil/myseat")
    public ResponseEntity<?> getReservationSeatActiveByReservationId(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("reservationId");
            Long userId = Long.parseLong(id);
            List<ProfileReservationSeatDTO> profileReservationDTOs = profileUserService.getReservationSeatByReservationId(userId);
            return new ResponseEntity<>(profileReservationDTOs, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping ("/load-ticket")
    Map<String,String> getTicket(@RequestBody Map<String, Object> request){
        try{
            System.out.println(request);
        Integer id = (Integer) request.get("reservationSeatId");
        return reservationSeatService.getTicket(id.longValue());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
