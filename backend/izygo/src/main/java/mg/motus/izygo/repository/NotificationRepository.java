package mg.motus.izygo.repository;

import mg.motus.izygo.model.Notification;

import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
}
