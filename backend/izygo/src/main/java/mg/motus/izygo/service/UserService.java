package  mg.motus.izygo.service;

import mg.motus.izygo.exception.IncorrectPasswordException;
import mg.motus.izygo.exception.UserNotFoundException;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder     = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public User login(String phoneNumber, String password) throws UserNotFoundException, IncorrectPasswordException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null)
            throw new UserNotFoundException();

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new IncorrectPasswordException();

        return user;
    }
}
