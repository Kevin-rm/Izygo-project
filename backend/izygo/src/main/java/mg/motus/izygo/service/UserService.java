package mg.motus.izygo.service;

import mg.motus.izygo.model.Notification;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.NotificationRepository;
import mg.motus.izygo.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

import java.util.List;

@Service
public class UserService {
    private final UserRepository         userRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder        passwordEncoder;

    public UserService(UserRepository userRepository, NotificationRepository notificationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );

        String phoneNumber = user.getPhoneNumber();
        if (phoneNumber.startsWith("0"))
            user.setPhoneNumber(phoneNumber.substring(1));

        return userRepository.save(user);
    }

    public List<Notification> findAllNotifications(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }
}
