package mg.motus.izygo.dto;

public record RouteStopDTO(
    Integer id,
    String  label,
    Integer lineId,
    String  lineLabel
) { }
