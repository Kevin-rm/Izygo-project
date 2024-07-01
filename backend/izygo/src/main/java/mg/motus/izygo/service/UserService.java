package mg.motus.izygo.service;

import mg.motus.izygo.model.Notification;
import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.NotificationRepository;
import mg.motus.izygo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
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

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<Notification> findAllNotifications(Long userId) {
        return notificationRepository.findAllByUserId(userId);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
