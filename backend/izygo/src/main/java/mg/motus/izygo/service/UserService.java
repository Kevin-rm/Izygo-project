package  mg.motus.izygo.service;

import mg.motus.izygo.model.Notification;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.NotificationRepository;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        return userRepository.save(user);
    }

    public List<Notification> findNotifications(int userId) {
        return notificationRepository.findByUserId(userId);
    }
}
