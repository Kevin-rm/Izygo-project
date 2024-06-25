package mg.motus.izygo.controller;

import jakarta.validation.Valid;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import org.springframework.http.HttpStatus;
import java.util.Map;
import java.util.HashMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
