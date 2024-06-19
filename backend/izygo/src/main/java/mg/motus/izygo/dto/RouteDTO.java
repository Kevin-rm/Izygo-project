package mg.motus.izygo.dto;

import java.util.List;

public record RouteDTO(
    List<List<RouteStopInfoDTO>> stops,
    short totalDuration,
    int   lineTransitionCount
) { }
