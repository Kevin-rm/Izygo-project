package mg.motus.izygo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@ToString(doNotUseGetters = true)
public class Notification {
    @Id
    private Long id;

    @NotNull(message = "Une notification doit être adressée à un utilisateur")
    private Long userId;

    private Long nextUserID;    

    @NotNull
    private Long  busId;

    @NotNull
    private Short seatId;

    @NotNull
    @NotBlank
    private String message;

    @NotNull
    private LocalDateTime sentAt;

    private Boolean isAccepted;
}
