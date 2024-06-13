package mg.motus.izygo.service;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.dto.StopDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ResearchService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ResearchService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<List<RouteDTO>> findRoute(int departureStopId, int arrivalStopId) {
        List<List<RouteDTO>> results = new ArrayList<>();

        for (Object[] objects : doFindRoute(departureStopId, arrivalStopId)) {
            List<RouteDTO> route = new ArrayList<>();

            Map<Integer, List<StopDTO>> stopMap = new TreeMap<>();
            List<Integer> lineIds = (List<Integer>) objects[2];

            for (int i = 0; i < lineIds.size(); i++) {
                Integer lineId = lineIds.get(i);

                stopMap.putIfAbsent(lineId, new ArrayList<>());
                stopMap.get(lineId).add(new StopDTO(
                    ((List<Integer>) objects[0]).get(i),
                    ((List<String>) objects[1]).get(i),
                    lineId,
                    ((List<String>) objects[3]).get(i)
                ));
            }

            route.add(new RouteDTO(
                new ArrayList<>(stopMap.values()),
                (Short) objects[4],
                (Integer) objects[5]
            ));
            results.add(route);
        }

        return results;
    }

    private List<Object[]> doFindRoute(int departureStopId, int arrivalStopId) {
        return jdbcTemplate.query("SELECT * FROM find_route(?, ?)", (rs, rowNum) -> {
            Object[] objects = new Object[6];
            objects[0] = Arrays.asList((Integer[]) rs.getArray("stop_ids").getArray());
            objects[1] = Arrays.asList((String[]) rs.getArray("stop_labels").getArray());
            objects[2] = Arrays.asList((Integer[]) rs.getArray("line_ids").getArray());
            objects[3] = Arrays.asList((String[]) rs.getArray("line_labels").getArray());
            objects[4] = rs.getShort("total_duration");
            objects[5] = rs.getInt("line_transition_count");

            return objects;
        }, departureStopId, arrivalStopId);
    }
}
