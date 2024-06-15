package mg.motus.izygo.controller;

import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
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
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public void login(@RequestBody Map<String, String> loginRequest) {
        String phoneNumber = loginRequest.get("phoneNumber");
        String password = loginRequest.get("password");
        System.out.println(phoneNumber );

        // try {
        //     User user = userService.checkLogin(phoneNumber, password);
        //     return ResponseEntity.ok(user);
        // } catch (RuntimeException e) {
        //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        // }
    }

}
