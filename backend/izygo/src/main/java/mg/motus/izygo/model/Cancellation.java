package mg.motus.izygo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Builder
@ToString(doNotUseGetters = true)
public class Cancellation {
    @Id
    private Long id;

    @NotNull
    private Long reservationSeatId;

    @NotNull
    private Boolean isActive;
}
