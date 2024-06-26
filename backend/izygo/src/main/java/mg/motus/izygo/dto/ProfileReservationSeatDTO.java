package mg.motus.izygo.dto;

import java.sql.Timestamp;

public record ProfileReservationSeatDTO(
    Long id,
    String lineLabel,
    String dateTime,
    Long reservationSeatId,
    String seatLabel
) {}
