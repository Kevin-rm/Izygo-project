package mg.motus.izygo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@ToString(doNotUseGetters = true)
public class Bus {
    @Id
    private Long id;

    @NotNull(message = "La matricule d'un bus doit être précisée")
    @NotBlank(message = "La matricule d'un bus ne peut pas être vide")
    private String licensePlate;

    @NotNull(message = "Le nombre de sièges d'un bus doit être précisé")
    @Positive(message = "Le nombre de sièges d'un bus ne peut pas être négatif ou nul")
    private Short numberOfSeats;

    @NotNull
    private Integer lineId;
}
