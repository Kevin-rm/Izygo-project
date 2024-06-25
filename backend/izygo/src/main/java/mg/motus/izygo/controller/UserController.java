package mg.motus.izygo.controller;

import jakarta.validation.Valid;
import mg.motus.izygo.model.Notification;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        user = userService.register(user);
        
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/notifications")
    public List<Notification> getNotifications(@PathVariable("id") Long userId) {
        return userService.findAllNotifications(userId);
    }
}
