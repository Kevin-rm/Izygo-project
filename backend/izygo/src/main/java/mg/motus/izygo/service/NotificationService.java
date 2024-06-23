package mg.motus.izygo.service;

import mg.motus.izygo.dto.NotificationParamsDTO;
import mg.motus.izygo.model.Notification;
import mg.motus.izygo.repository.NotificationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final JdbcTemplate jdbcTemplate;

    public NotificationService(NotificationRepository notificationRepository, JdbcTemplate jdbcTemplate) {
        this.notificationRepository = notificationRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * Reaction functionnalities for Notifications
     */
    public void accept(Notification n) {
        n.setIsAccepted(true);
        this.notificationRepository.save(n);
    }
    public void decline(Notification n) {
        n.setIsAccepted(false);
        this.notificationRepository.save(n);
    }

    /*
     * Notifications have a nullable Boolean state,
     * indicating whether the destined user has reacted to them or not.
     * This function will return true if the notification has been accepted,
     * false if it has been declined or ignored AND there is another user to forward it to.
     */
    public boolean hasReactionState(Notification n) {
        return !((n.getIsAccepted() == null || !n.getIsAccepted()) && n.getNextUserID() != null);
    }

    /*
     * Call to the matching SQL function to insert a notification:
     * it'll automatically find the next user to notify
     */
    public Long insertNotification(NotificationParamsDTO params) {
        String sql = "SELECT insert_notification(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.query(sql, rs -> {
            if (rs.next()) {
                return rs.getLong(1);
            }
            return null;
        }, params.userId(), params.message(), params.busToFollowId(), params.seatId(), params.departureStop(), params.arrivalStop());
    }

    /*
     * Waits the given delay (in milliseconds)
     * then fetches the notification from the database and checks its reaction values.
     * If there was no accepting reaction (the user either declined or ignored the notification),
     * the method returns true, false otherwise.
     */
    @Async
    public CompletableFuture<Boolean> shouldInsert(Long notificationId, long millisDelay) throws InterruptedException {
        Thread.sleep(millisDelay);

        Notification n = this.notificationRepository.findById(notificationId).get();
        return CompletableFuture.completedFuture(!hasReactionState(n));
    }
}
