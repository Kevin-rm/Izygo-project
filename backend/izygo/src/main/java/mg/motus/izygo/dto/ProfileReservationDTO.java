package mg.motus.izygo.dto;

public record ProfileReservationDTO(
    Long id,
    String lineLabel,
    String dateTime,
    int nbReservationSeat,
    Boolean isActive,
    int nbReservationSeatInit
) {}
