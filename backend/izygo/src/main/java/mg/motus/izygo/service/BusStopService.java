package mg.motus.izygo.service;

import mg.motus.izygo.model.BusStop;
import mg.motus.izygo.repository.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopService {

    @Autowired
    private BusStopRepository busStopRepository;

    public List<BusStop> getStopsByLine(int lineId) {
        return busStopRepository.getStopsByLine(lineId);
    }
}
