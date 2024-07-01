package mg.motus.izygo.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    private final JdbcTemplate jdbcTemplate;

    public DashboardService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Double profitInPeriod(Timestamp dateMin, Timestamp dateMax) {
        return jdbcTemplate.queryForObject("SELECT * FROM profit_in_period(?, ?)",
            Double.class,
            dateMin,
            dateMax
        );
    }

    public List<Map<String, Object>> profitThroughYears() {
        return jdbcTemplate.queryForList("SELECT * FROM profit_through_years()");
    }

    public Double countReservationsInPeriod(
        Timestamp dateMin, Timestamp dateMax
    ) {
        return jdbcTemplate.queryForObject("SELECT * FROM count_reservations_in_period(?, ?)",
            Double.class,
            dateMin,
            dateMax
        );
    }

    public List<Map<String, Object>> reservationsThroughYears() {
        return jdbcTemplate.queryForList("SELECT * FROM reservations_through_years()");
    }
}
