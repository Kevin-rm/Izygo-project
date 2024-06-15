package mg.motus.izygo.controller;

import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import mg.motus.izygo.config.CorsConfiguration ;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> loginRequest) {
        User user = User.builder()
                .firstname(loginRequest.get("nom"))
                .lastname(loginRequest.get("prenom"))
                .phoneNumber(loginRequest.get("phone"))
                .password(loginRequest.get("pass"))
                .roleId((short) 1)
                .build();
        try {
            User createdUser = userService.registerUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (Exception e) {
            // Gérer les exceptions spécifiques et renvoyer le bon statut HTTP
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de l'inscription. Veuillez vérifier vos informations.");
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String phoneNumber = loginRequest.get("phoneNumber");
        String password = loginRequest.get("password");
        try {
            User user = userService.checkLogin(phoneNumber, password);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Échec de la connexion. Veuillez vérifier vos informations.");
        }
    }

}
