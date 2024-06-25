package mg.motus.izygo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@Builder
@ToString(doNotUseGetters = true)
public class ReservationSeat {
    @Id
    private Long id;

    @NotNull
    private Short seatId;

    @NotNull
    private Long reservationId;

    @NotNull
    @Builder.Default
    private Boolean isActive = true;

    @NotNull
    @Builder.Default
    private Boolean onBus = false;

}
