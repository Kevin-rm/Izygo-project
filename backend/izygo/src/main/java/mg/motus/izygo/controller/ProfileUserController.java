// package mg.motus.izygo.controller;

// import mg.motus.izygo.model.ProfileUser;
// import mg.motus.izygo.service.ProfileUserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/profileuser")
// public class ProfileUserController {
//     private final ProfileUserService  profileUserService;

//     @Autowired
//     public ProfileUserController( ProfileUserService profileUserService) {
//         this.profileUserService =  profileUserService;
//     }

//     @GetMapping("/user/{userId}")
//     public List<ProfileUser> getProfileUser(@PathVariable Long userId) {
//         return  profileUserService.getProfileUserId(userId);
//     }
// }
