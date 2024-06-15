package  mg.motus.izygo.service;

import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    }

    public User registerUser(@Valid User user) {
        return userRepository.save(user);
    }

    public User checkLogin(String phoneNumber, String password, PasswordEncoder passwordEncoder) throws RuntimeException {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new RuntimeException("Utilisateur avec le numéro de téléphone " + phoneNumber + " n'existe pas.");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec le numéro de téléphone " + phoneNumber);
        }
        return user;
    }
}
