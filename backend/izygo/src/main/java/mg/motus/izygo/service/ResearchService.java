package mg.motus.izygo.service;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.dto.RouteStopDTO;
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

        for (RouteData data : fetchRouteData(departureStopId, arrivalStopId))
            results.add(Collections.singletonList(
                new RouteDTO(
                    new ArrayList<>(buildStopMap(data).values()),
                    data.totalDuration(),
                    data.lineTransitionCount()
                )
            ));

        return results;
    }

    private Map<Integer, List<RouteStopDTO>> buildStopMap(RouteData data) {
        Map<Integer, List<RouteStopDTO>> stopMap = new TreeMap<>();
        List<Integer> stopIds    = data.stopIds();
        List<String>  stopLabels = data.stopLabels();
        List<Integer> lineIds    = data.lineIds();
        List<String>  lineLabels = data.lineLabels();

        for (int i = 0; i < lineIds.size(); i++) {
            Integer lineId = lineIds.get(i);
            stopMap.putIfAbsent(lineId, new ArrayList<>());
            stopMap.get(lineId).add(new RouteStopDTO(
                stopIds.get(i),
                stopLabels.get(i),
                lineId,
                lineLabels.get(i)
            ));
        }

        return stopMap;
    }

    private List<RouteData> fetchRouteData(int departureStopId, int arrivalStopId) {
        return jdbcTemplate.query("SELECT * FROM find_route(?, ?)", (rs, rowNum) -> new RouteData(
            Arrays.asList((Integer[]) rs.getArray("stop_ids").getArray()),
            Arrays.asList((String[]) rs.getArray("stop_labels").getArray()),
            Arrays.asList((Integer[]) rs.getArray("line_ids").getArray()),
            Arrays.asList((String[]) rs.getArray("line_labels").getArray()),
            rs.getShort("total_duration"),
            rs.getInt("line_transition_count")
        ), departureStopId, arrivalStopId);
    }

    private record RouteData(
        List<Integer> stopIds,
        List<String>  stopLabels,
        List<Integer> lineIds,
        List<String>  lineLabels,
        short totalDuration,
        int   lineTransitionCount
    ) { }
}
