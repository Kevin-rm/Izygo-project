package mg.motus.izygo.dto;

import java.util.List;

public record RouteDTO(
    List<List<StopDTO>> stops,
    Short   totalDuration,
    Integer lineTransitionCount
) { }
