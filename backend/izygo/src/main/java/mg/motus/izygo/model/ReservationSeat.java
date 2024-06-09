package mg.motus.izygo.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

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
    private int startStopId;

    @NotNull 
    private int endStopId;
}
