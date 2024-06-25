package mg.motus.izygo.dto;

public record ReservationDTO(
    Long id,
    Long reservationSeatId,
    String firstname,
    String lastname,
    String licensePlate,
    String seatLabel,
    String lineLabel,
    String startStop,
    String endStop
) { }
