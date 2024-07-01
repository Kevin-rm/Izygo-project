package mg.motus.izygo.repository;

import mg.motus.izygo.dto.BusLineDTO;
import mg.motus.izygo.dto.LinePathDTO;
import mg.motus.izygo.model.BusLine;
import mg.motus.izygo.model.BusStop;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface BusLineRepository extends CrudRepository<BusLine, Integer> {
    @Query(
        value = "SELECT * FROM get_ordered_line_path(:lineId)",
        rowMapperClass = BusLinePathDTORowMapper.class
    )
    List<LinePathDTO> findPath(int lineId);
    


    @Query(value = "SELECT line_id,\n" +
        "       line_label,\n" +
        "       stop_id,\n" +
        "       stop_label,\n" +
        "       latitude,\n" +
        "       longitude\n" +
        "FROM v_line_stop",
        resultSetExtractorClass = BusLineDTOResultSetExtractor.class
    )
    List<BusLineDTO> findAllWithStops();

    class BusLineDTOResultSetExtractor implements ResultSetExtractor<List<BusLineDTO>> {
        @Override
        public List<BusLineDTO> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, BusLineDTO> busLineMap = new HashMap<>();

            while (rs.next()) {
                Integer lineId       = rs.getInt("line_id");
                String lineLabel     = rs.getString("line_label");
                BigDecimal latitude  = rs.getBigDecimal("latitude");
                BigDecimal longitude = rs.getBigDecimal("longitude");

                BusStop busStop = BusStop.builder()
                    .id(rs.getInt("stop_id"))
                    .label(rs.getString("stop_label"))
                    .latitude(latitude == null ? null : latitude.doubleValue())
                    .longitude(longitude == null ? null : longitude.doubleValue())
                    .build();

                if (busLineMap.containsKey(lineId)) {
                    busLineMap.get(lineId).stops().add(busStop);
                    continue;
                }

                List<BusStop> stops = new ArrayList<>();
                stops.add(busStop);
                BusLineDTO busLineDTO = new BusLineDTO(lineId, lineLabel, stops);
                busLineMap.put(lineId, busLineDTO);
            }

            return new ArrayList<>(busLineMap.values());
        }
    }

    class BusLinePathDTORowMapper implements RowMapper<LinePathDTO> {

        @Override
        public LinePathDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LinePathDTO(
                Arrays.asList((String[]) rs.getArray("path").getArray()),
                rs.getShort("total_duration")
            );
        }
    }
}
