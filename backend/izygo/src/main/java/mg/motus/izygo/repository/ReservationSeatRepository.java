package mg.motus.izygo.repository;

import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.model.ReservationSeat;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationSeatRepository extends CrudRepository<ReservationSeat, Long> {
    List<ReservationSeat> findByIsActiveTrue();

    @Query(value = "SELECT id, " +
            "reservation_seat_id, " +
            "firstname, " +
            "lastname, " +
            "license_plate, " +
            "line_label, " +
            "seat_label, " +
            "start_stop, " +
            "end_stop " +
            "FROM v_reservation " +
            "WHERE id = :id")
    List<ReservationDTO> findReservationsById(@Param("id") Long id);
}
