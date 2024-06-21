package  mg.motus.izygo.service;

import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder     = new BCryptPasswordEncoder();
    }

    public User register(User user) {
        return userRepository.save(user);
    }

    public User login(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null)
            return null;
        if (!passwordEncoder.matches(password, user.getPassword()))
            return null;

        return user;
    }
}
