package mg.motus.izygo.service;

import mg.motus.izygo.exception.*;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Inscription de l'utilisateur
    public User registerUser(@Valid User user) {
        return userRepository.save(user);
    }

    //Connexion a la page
    public User checkLogin(String phoneNumber, String password) {

        User user = userRepository.findByPhoneNumber(phoneNumber);

        if (user == null) {
            throw new UserNotFoundException("Utilisateur avec le numéro de téléphone " + phoneNumber + " n'existe pas.");
        }

        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException("Mot de passe incorrect pour l'utilisateur avec le numéro de téléphone " + phoneNumber);
        }

        return user;
    }
}
