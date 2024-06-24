package mg.motus.izygo.repository;

import mg.motus.izygo.model.Reservation;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    @Query(
        value = "SELECT rs.seat_id as reserved_seat " +
                "FROM v_reservation AS rs " +
                "WHERE rs.departure_stop_id <= :departureStopId " +
                "AND rs.arrival_stop_id > :departureStopId " +
                "AND rs.bus_id = :busId"
    )
    List<Integer> findReservedSeatsByBusId(@Param("busId") Long busId, @Param("departureStopId") Long departureStopId);
}
