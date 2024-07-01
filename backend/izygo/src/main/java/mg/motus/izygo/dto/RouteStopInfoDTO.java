package mg.motus.izygo.dto;

public record RouteStopInfoDTO(
    Integer id,
    String  label,
    Integer lineId,
    String  lineLabel,
    Double  latitude,
    Double  longitude
) { }
