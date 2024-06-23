package mg.motus.izygo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("stop")
@Getter
@Builder
@ToString(doNotUseGetters = true)
public class BusStop {
    @Id
    private Integer id;

    @NotNull(message = "Le libellé d'un arret de bus doit être précisé")
    @NotBlank(message = "Le libellé d'un arret de bus ne peut pas être vide")
    private String label;
}
