package mg.motus.izygo.dto;

import java.util.List;

public record LinePathDTO(
    List<String> stopLabels,
    short totalDuration
) { }
