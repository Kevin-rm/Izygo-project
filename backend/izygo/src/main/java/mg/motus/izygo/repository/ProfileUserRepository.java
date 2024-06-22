package mg.motus.izygo.repository;

import mg.motus.izygo.dto.ReservationbyuserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileUserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationbyuserDTO> findByUserId(Long userId) {
        String sql = "SELECT user_id, reservation_id, reservation_date, number_of_seats, bus_line, bus_id " +
                     "FROM v_reservationbyuser " +
                     "WHERE user_id = ?";
        
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ReservationbyuserDTO(
                        rs.getLong("user_id"),
                        rs.getLong("reservation_id"),
                        rs.getTimestamp("reservation_date").toLocalDateTime(),
                        rs.getInt("number_of_seats"),
                        rs.getString("bus_line"),
                        rs.getLong("bus_id")
                ), userId);
    }
}
