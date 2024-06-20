package mg.motus.izygo.service;

import mg.motus.izygo.repository.BusRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

import mg.motus.izygo.dto.ProfitDTO;
import mg.motus.izygo.dto.ReservationCountDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AdminService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Functions for calculating profit

    /*
     * This function will return the total sum of our profit
     * between dateMin and dateMax (both inclusive)
     * Therefore, if I need to find the profit in a day, i could totally just input the start of the day at 00:00:01 and the end of the day at 23:59:59
     */
    public double profitInPeriod(LocalDateTime dateMin, LocalDateTime dateMax) {
        double profit = 0;

        String sql = " SELECT profit_in_period(?, ?)";

        profit = jdbcTemplate.query(sql, new ResultSetExtractor<Double>() {
            @Override
            public Double extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
                return null;
            }
        }, new Object[] {Timestamp.valueOf(dateMin), Timestamp.valueOf(dateMax)});

        return profit;
    }

    /*
     * The profits throughout all the years of activity will be fetched inside this Map: the key is the year and the value is the profit made
     * LinkedHashMap is used to preserve the order of insertion of the data
     */
    public LinkedHashMap<Integer, Double> profitThroughYears() {
        LinkedHashMap<Integer, Double> profits = new LinkedHashMap<>();

        List<ProfitDTO> profitList = jdbcTemplate.query(" SELECT * FROM profit_through_years()", (rs, rownum) -> {
            int year = rs.getInt("year");
            double profit = rs.getBigDecimal("profit").doubleValue();

            return new ProfitDTO(year, profit);
        });

        for (ProfitDTO p : profitList) {
            profits.put(p.year(), p.profit());
        }

        return profits;
    }

    // Functions for calculating the number of reservations made

    /*
     * This function will return the total number of reservations (NOT reservation_seat)
     * between dateMin and dateMax (both inclusive)
     * Therefore, if I need to find the profit in a day, i could totally just input the start of the day at 00:00:01 and the end of the day at 23:59:59
     */
    public int reservationsInPeriod(LocalDateTime dateMin, LocalDateTime dateMax) {
        int reservationCount = 0;

        String sql = " SELECT count_reservations_in_period(?, ?)";

        reservationCount = jdbcTemplate.query(sql, new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return null;
            }
        }, new Object[] {Timestamp.valueOf(dateMin), Timestamp.valueOf(dateMax)});

        return reservationCount;
    }

    /*
     * The number of reservations (NOT reservation_seat) throughout all the years of activity will be fetched inside this Map: the key is the year and the value is the count of reservations
     * LinkedHashMap is used to preserve the order of insertion of the data
     */
    public LinkedHashMap<Integer, Integer> reservationsThroughYears() {
        LinkedHashMap<Integer, Integer> reservationCounts = new LinkedHashMap<>();

        List<ReservationCountDTO> countList = jdbcTemplate.query("SELECT * FROM reservations_through_years()", (rs, rowNum) -> {
            int year = rs.getInt("year");
            int rCount = rs.getInt("reservation_count");

            return new ReservationCountDTO(year, rCount);
        });

        for (ReservationCountDTO rCount : countList) {
            reservationCounts.put(rCount.year(), rCount.count());
        }

        return reservationCounts;
    }
}
