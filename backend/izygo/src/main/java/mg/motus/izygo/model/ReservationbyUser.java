package mg.motus.izygo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Table(name = "v_reservationbyuser")
public class ReservationbyUser {

    @Id
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reservation_date")
    private String reservationDate;

    @Column(name = "number_of_seats")
    private Long numberOfSeats;

    @Column(name = "bus_line")
    private String busLine;

    @Column(name = "bus_id")
    private Long busId;
}

