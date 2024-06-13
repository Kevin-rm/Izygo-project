package mg.motus.izygo.dto;

public record StopDTO(
    Integer id,
    String  label,
    Integer lineId,
    String  lineLabel
) { }
