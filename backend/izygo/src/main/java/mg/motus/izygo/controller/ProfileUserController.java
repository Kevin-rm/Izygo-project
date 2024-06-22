package mg.motus.izygo.controller;

import mg.motus.izygo.dto.ReservationbyuserDTO;
import mg.motus.izygo.service.ProfileUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profileuser")
public class ProfileUserController {

    private final ProfileUserService profileUserService;

    @Autowired
    public ProfileUserController(ProfileUserService profileUserService) {
        this.profileUserService = profileUserService;
    }

    @GetMapping("/user/{userId}")
    public List<ReservationbyuserDTO> getProfileUser(@PathVariable Long userId) {
        return profileUserService.getReservationsByUserId(userId);
    }
}
