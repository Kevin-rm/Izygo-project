package mg.motus.izygo.service;

import mg.motus.izygo.exception.IncorrectPasswordException;
import mg.motus.izygo.exception.UserNotFoundException;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String phoneNumber, String password) throws UserNotFoundException, IncorrectPasswordException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        System.out.println(user);
        if (user == null)
            throw new UserNotFoundException();

        if (!(
            passwordEncoder.matches(password, user.getPassword()) ||
            password.equals(user.getPassword())
        ))
            throw new IncorrectPasswordException();

        return user;
    }
}
