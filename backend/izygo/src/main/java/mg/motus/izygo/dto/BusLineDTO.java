package mg.motus.izygo.dto;

import mg.motus.izygo.model.BusStop;

import java.util.List;

public record BusLineDTO(
    Integer id,
    String  label,
    List<BusStop> stops
) { }
