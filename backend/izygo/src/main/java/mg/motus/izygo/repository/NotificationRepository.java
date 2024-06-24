package mg.motus.izygo.repository;

import mg.motus.izygo.model.Notification;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> findAllByUserId(Long userId);
}
