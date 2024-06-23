package mg.motus.izygo.repository;

import mg.motus.izygo.model.BusStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BusStopRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<BusStop> getStopsByLine(int lineId) {
        String sql = "SELECT * FROM get_stops_by_line(:lineId)";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("lineId", lineId);
        return namedParameterJdbcTemplate.query(sql, parameters, new BusStopRowMapper());
    }

    private static final class BusStopRowMapper implements RowMapper<BusStop> {
        @Override
        public BusStop mapRow(ResultSet rs, int rowNum) throws SQLException {
            return BusStop.builder()
                    .id(rs.getInt("stop_id"))
                    .label(rs.getString("stop_label"))
                    .build();
        }
    }
}
