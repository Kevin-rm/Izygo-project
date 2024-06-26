package mg.motus.izygo.controller;

import ch.qos.logback.core.model.INamedModel;
import jakarta.validation.Valid;
import mg.motus.izygo.model.Notification;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/update-account-balance")
    public ResponseEntity<?> updateAccountBalance(@RequestBody Map<String, Object> request) {
        Map<String, String> response = new HashMap<>();
        Integer userId = (Integer) request.get("userId");
        Integer accountBalance = (Integer) request.get("accountBalance");

        User user = userService.findById(userId.longValue()).get();
        String password = (String) request.get("password");
        int error = 0;

        if (accountBalance <= 0) {
            response.put("accountBalance", "Le montant doit être positif");
            error++;
        }
        if (!(
            userService.getPasswordEncoder().matches(password, user.getPassword()) ||
                password.equals(user.getPassword())
        )) {
            response.put("password", "Mot de passe incorrect");
            error++;
        }

        if (error > 0)
            return ResponseEntity.badRequest().body(response);

        user.setAccountBalance(user.getAccountBalance() + accountBalance.doubleValue());
        userService.save(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}/notifications")
    public List<Notification> getNotifications(@PathVariable("id") Long userId) {
        return userService.findAllNotifications(userId);
    }
}
