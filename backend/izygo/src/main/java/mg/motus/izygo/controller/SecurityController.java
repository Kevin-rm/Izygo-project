package mg.motus.izygo.controller;

import mg.motus.izygo.exception.IncorrectPasswordException;
import mg.motus.izygo.exception.UserNotFoundException;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SecurityController {
    private final AuthenticationService authenticationService;

    public SecurityController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequestData) {
        User user;
        try {
            user = authenticationService.login(
                loginRequestData.get("phoneNumber"),
                loginRequestData.get("password")
            );
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }

        return ResponseEntity.ok(user);
    }
}
