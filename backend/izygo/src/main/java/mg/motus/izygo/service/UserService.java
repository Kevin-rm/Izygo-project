package  mg.motus.izygo.service;

import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

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

    public User checkLogin(String phoneNumber, String password) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new RuntimeException("Utilisateur avec le numéro de téléphone " + phoneNumber + " n'existe pas.");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Mot de passe incorrect pour l'utilisateur avec le numéro de téléphone " + phoneNumber);
        }
        return user;
    }
}
