package mg.motus.izygo.dto;

import java.time.LocalDateTime;

public record BusArrivalDTO(
    Long    busId,
    Integer lineId,
    Integer stopId,
    LocalDateTime dateTimePassage
) {
}
