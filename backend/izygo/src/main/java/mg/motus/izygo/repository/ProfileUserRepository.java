package mg.motus.izygo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mg.motus.izygo.dto.ProfileReservationDTO;
import mg.motus.izygo.dto.ProfileReservationSeatDTO;


@Repository
public interface ProfileUserRepository extends CrudRepository<ProfileReservationDTO, Long> {

     @Query(value = "SELECT " + 
                  "    id, " + 
                  "    line_label, " + 
                  "    date_time, " + 
                  "    COUNT(reservation_seat_id) FILTER (WHERE is_active) AS nb_reservation_seat, " + 
                  "    (COUNT(reservation_seat_id) FILTER (WHERE is_active) > 0) AS is_active, " + 
                  "     COUNT(reservation_seat_id) as nb_reservation_seat_init "+
                  "FROM " + 
                  "    v_reservation " + 
                  "WHERE user_id = :userId " +
                  "GROUP BY " + 
                  "    id,line_label,date_time")
    List<ProfileReservationDTO> findReservationsByUserId(@Param("userId") Long userId);
   
    @Query(value = "SELECT " + 
                "    id, " + 
                "    line_label, " + 
                "    date_time, " + 
                "    reservation_seat_id, " + 
                "    seat_label " + 
                "FROM " + 
                "    v_reservation " + 
                "WHERE " + 
                "    id = :reservationId " + 
                "AND " + 
                "    is_active = true")
    List<ProfileReservationSeatDTO> findReservationSeatByReservationId(@Param("reservationId") Long reservationId);
}
