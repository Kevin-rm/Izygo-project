package mg.motus.izygo.repository;

import mg.motus.izygo.dto.BusArrivalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Repository
public class ResearchRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResearchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BusArrivalDTO> findFutureArrivingBuses(
        int departureStopId,
        Timestamp timestamp1,
        Timestamp timestamp2,
        String margin
    ) {
        return jdbcTemplate.query(
            "SELECT * FROM find_future_arriving_buses(?, ?::TIMESTAMP, ?::TIMESTAMP, ?::INTERVAL)",
            (rs, rowNum) -> new BusArrivalDTO(
                rs.getLong("bus_id"),
                rs.getInt("line_id"),
                rs.getInt("stop_id"),
                rs.getTimestamp("date_time_passage").toLocalDateTime()
            ),
            departureStopId, timestamp1, timestamp2, margin
        );
    }

    public List<RouteData> fetchRouteData(int departureStopId, int arrivalStopId) {
        return jdbcTemplate.query("SELECT * FROM find_route(?, ?)", (rs, rowNum) -> new RouteData(
            Arrays.asList((Integer[]) rs.getArray("stop_ids").getArray()),
            Arrays.asList((String[]) rs.getArray("stop_labels").getArray()),
            Arrays.asList((Integer[]) rs.getArray("line_ids").getArray()),
            Arrays.asList((String[]) rs.getArray("line_labels").getArray()),
            rs.getShort("total_duration"),
            rs.getInt("line_transition_count")
        ), departureStopId, arrivalStopId);
    }

    public record RouteData(
        List<Integer> stopIds,
        List<String>  stopLabels,
        List<Integer> lineIds,
        List<String>  lineLabels,
        short totalDuration,
        int   lineTransitionCount
    ) { }
}
