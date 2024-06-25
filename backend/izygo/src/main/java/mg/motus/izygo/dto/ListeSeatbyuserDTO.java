package mg.motus.izygo.dto;

import java.time.LocalDateTime;

public record ListeSeatbyuserDTO(
    Long userId,
    Long reservationId,
    LocalDateTime reservationDate,
    String busLine,
    String seatLabel
) { }
