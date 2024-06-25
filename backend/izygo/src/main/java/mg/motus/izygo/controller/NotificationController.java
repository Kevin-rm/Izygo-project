package mg.motus.izygo.controller;

import mg.motus.izygo.model.Notification;
import mg.motus.izygo.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptNotification(@RequestBody Notification notification) {
        notificationService.accept(notification);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/decline")
    public ResponseEntity<Void> declineNotification(@RequestBody Notification notification) {
        notificationService.decline(notification);
        return ResponseEntity.noContent().build();
    }
}
