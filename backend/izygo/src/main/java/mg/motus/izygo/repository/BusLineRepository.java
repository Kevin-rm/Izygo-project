package mg.motus.izygo.repository;

import mg.motus.izygo.dto.LinePathDTO;
import mg.motus.izygo.model.BusLine;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public interface BusLineRepository extends CrudRepository<BusLine, Long> {
    @Query(
        value = "SELECT * FROM get_ordered_line_path(:lineId)",
        rowMapperClass = BusLinePathDTORowMapper.class
    )
    List<LinePathDTO> findPath(int lineId);

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
