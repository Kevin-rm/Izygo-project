package mg.motus.izygo.service;

import mg.motus.izygo.dto.SeatDTO;
import mg.motus.izygo.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<SeatDTO> findAllBusSeats() {
        return busRepository.findAllBusSeats();
    }
}
