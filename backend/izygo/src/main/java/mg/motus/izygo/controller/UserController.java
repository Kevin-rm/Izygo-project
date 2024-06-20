package mg.motus.izygo.controller;

import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import mg.motus.izygo.config.CorsConfiguration ;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> loginRequest) {
        String hashedPassword = passwordEncoder.encode(loginRequest.get("pass"));
        User user = User.builder()
                .firstname(loginRequest.get("nom"))
                .lastname(loginRequest.get("prenom"))
                .phoneNumber(loginRequest.get("phone"))
                .password(hashedPassword)
                .roleId((short) 1)
                .build();
        try {
            User createdUser = userService.registerUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            System.out.println(e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String phoneNumber = loginRequest.get("phoneNumber");
        String password = loginRequest.get("password");
        try {
            User user = userService.checkLogin(phoneNumber, password, this.passwordEncoder);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            System.out.println(e);
            // Prepare error response as JSON
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }
    }

}
