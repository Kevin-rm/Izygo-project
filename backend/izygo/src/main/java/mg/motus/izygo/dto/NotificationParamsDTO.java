package mg.motus.izygo.dto;

public record NotificationParamsDTO(
    Long    userId,
    String  message,
    Long    busToFollowId,
    Short   seatId,
    Integer departureStop,
    Integer arrivalStop
) { }
