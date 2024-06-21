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
        if (lineId < 0)
            throw new IllegalArgumentException("L'identifiant de la ligne de bus dont on veut connaître le trajet doit être positif");

        return busLineRepository.findPath(lineId);
    }
}
