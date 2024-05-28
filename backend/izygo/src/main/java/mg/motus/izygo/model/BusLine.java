package mg.motus.izygo.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("line")
@Getter
@Builder
@ToString(doNotUseGetters = true)
public class BusLine {
    @Id
    private Integer id;

    @NotNull(message = "Le libellé d'une ligne de bus doit être précisé")
    @NotBlank(message = "Le libellé d'une ligne de bus ne peut pas être vide")
    private String label;
}
