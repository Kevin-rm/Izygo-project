package mg.motus.izygo.service;

import mg.motus.izygo.dto.BusArrivalDTO;
import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.dto.RouteStopDTO;
import mg.motus.izygo.repository.ResearchRepository;
import mg.motus.izygo.repository.ResearchRepository.RouteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ResearchService {
    private final ResearchRepository researchRepository;

    @Autowired
    public ResearchService(ResearchRepository researchRepository) {
        this.researchRepository = researchRepository;
    }

    public List<BusArrivalDTO> findFutureArrivingBuses(
        int departureStopId,
        Timestamp timestamp1,
        Timestamp timestamp2,
        String margin
    ) {
        return researchRepository.findFutureArrivingBuses(departureStopId, timestamp1, timestamp2, margin);
    }

    public List<List<RouteDTO>> findRoute(int departureStopId, int arrivalStopId) {
        List<List<RouteDTO>> results = new ArrayList<>();

        for (RouteData data : researchRepository.fetchRouteData(departureStopId, arrivalStopId))
            results.add(Collections.singletonList(
                new RouteDTO(
                    new ArrayList<>(buildStopMap(data).values()),
                    data.totalDuration(),
                    data.lineTransitionCount()
                )
            ));

        return results;
    }

    private Map<Integer, List<RouteStopDTO>> buildStopMap(ResearchRepository.RouteData data) {
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
}
