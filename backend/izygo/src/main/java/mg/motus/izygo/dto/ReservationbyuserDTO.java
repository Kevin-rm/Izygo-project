package mg.motus.izygo.dto;

import java.time.LocalDateTime;

public record ReservationbyuserDTO(
    Long userId,
    Long reservationId,
    LocalDateTime reservationDate,
    int numberOfSeats,
    String busLine,
    Long busId
) { }
