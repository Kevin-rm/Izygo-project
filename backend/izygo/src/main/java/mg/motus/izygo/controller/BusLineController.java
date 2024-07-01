package mg.motus.izygo.controller;

import mg.motus.izygo.dto.BusLineDTO;
import mg.motus.izygo.service.BusLineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bus-line")
public class BusLineController {

    private final BusLineService busLineService;

    public BusLineController(BusLineService busLineService) {
        this.busLineService = busLineService;
    }

    @GetMapping
    public List<BusLineDTO> getAllWithStops() {
        return busLineService.findAllWithStops();
    }
}
