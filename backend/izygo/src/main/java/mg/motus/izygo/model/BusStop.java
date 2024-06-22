package mg.motus.izygo.model;

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

    @NotNull
    private String label;
}
