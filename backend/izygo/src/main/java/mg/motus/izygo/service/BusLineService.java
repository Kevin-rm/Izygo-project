package mg.motus.izygo.service;

import mg.motus.izygo.dto.LinePathDTO;
import mg.motus.izygo.repository.BusLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusLineService {
    private final BusLineRepository busLineRepository;

    @Autowired
    public BusLineService(BusLineRepository busLineRepository) {
        this.busLineRepository = busLineRepository;
    }

    public List<LinePathDTO> findPath(int lineId) {
        return busLineRepository.findPath(lineId);
    }
}
