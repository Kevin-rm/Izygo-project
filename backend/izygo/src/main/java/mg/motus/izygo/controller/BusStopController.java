package mg.motus.izygo.controller;

import mg.motus.izygo.model.BusStop;
import mg.motus.izygo.service.BusStopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/arrets-bus")
public class BusStopController {

    private final BusStopService busStopService;

    public BusStopController(BusStopService busStopService) {
        this.busStopService = busStopService;
    }

    @GetMapping
    public List<BusStop> getAll() {
        return busStopService.findAll();
    }
}
