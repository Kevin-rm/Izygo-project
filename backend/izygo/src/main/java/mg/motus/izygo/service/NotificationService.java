package mg.motus.izygo.service;

import mg.motus.izygo.model.Notification;
import mg.motus.izygo.repository.NotificationRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private final JdbcTemplate template;

    @Autowired
    public NotificationService(NotificationRepository notifRepository, JdbcTemplate template) {
        this.notificationRepository = notifRepository;
        this.template = template;
    }

    /*
     * Notifications have a nullable Boolean state,
     * indicating whether the destined user has reacted to them or not.
     * This function will return true if the notification has been accepted,
     * false if it has been declined or ignored AND there is another user to forward it to.
     */
    public boolean checkReactionState(Notification n) {
        return !((n.getIsAccepted() == null || !n.getIsAccepted()) && n.getNextUserID() != null);
    }

    /*
     * Call to the matching SQL function to insert a notification:
     * it'll automatically find the next user to notify
     */
    public Long insertNotification(Long userId, String message, Long busToFollowId, Short seatId, Integer departureStop, Integer arrivalStop) {
        String sql = "SELECT insert_notification(?, ?, ?, ?, ?, ?)";
        return template.query(sql, new ResultSetExtractor<Long>() {
            @Override
            public Long extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return null;
            }
        }, new Object[]{userId, message, busToFollowId, seatId, departureStop, arrivalStop});
    }

}
