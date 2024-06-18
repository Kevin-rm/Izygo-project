package mg.motus.izygo.dto;

import java.util.List;

public record RouteDTO(
    List<List<RouteStopDTO>> stops,
    short totalDuration,
    int   lineTransitionCount
) { }
