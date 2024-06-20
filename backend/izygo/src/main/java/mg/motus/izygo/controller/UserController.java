package mg.motus.izygo.controller;

import jakarta.validation.Valid;
import mg.motus.izygo.exception.IncorrectPasswordException;
import mg.motus.izygo.exception.UserNotFoundException;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
        @Valid @RequestBody User user,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors())
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        user = userService.register(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> loginRequestData) throws UserNotFoundException, IncorrectPasswordException {
        User user = userService.login(
            loginRequestData.get("phoneNumber"),
            loginRequestData.get("password")
        );

        return ResponseEntity.ok(user);
    }
}
