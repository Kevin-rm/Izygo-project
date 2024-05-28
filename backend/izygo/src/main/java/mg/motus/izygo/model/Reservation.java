package mg.motus.izygo.model;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString(doNotUseGetters = true)
public class Reservation {
    @Id
    private Long id;

    @NotNull(message = "La date et l'heure de réservation doivent être précisées")
    private LocalDateTime dateTime;

    @NotNull
    private Long userId;

    @NotNull
    private Long busId;
}
