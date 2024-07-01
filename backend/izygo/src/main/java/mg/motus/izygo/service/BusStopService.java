package mg.motus.izygo.service;

import mg.motus.izygo.model.BusStop;
import mg.motus.izygo.repository.BusStopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopService {
    private final BusStopRepository busStopRepository;

    public BusStopService(BusStopRepository busStopRepository) {
        this.busStopRepository = busStopRepository;
    }

    public List<BusStop> findAll() {
        return (List<BusStop>) busStopRepository.findAll();
    }
}
