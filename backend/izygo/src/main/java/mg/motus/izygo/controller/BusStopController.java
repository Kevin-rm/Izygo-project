package mg.motus.izygo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mg.motus.izygo.dto.LinePathDTO;
import mg.motus.izygo.model.BusLine;
import mg.motus.izygo.model.BusStop;
import mg.motus.izygo.service.BusLineService;
import mg.motus.izygo.service.BusStopService;

@RestController
@RequestMapping("/api")
public class BusStopController {

    @Autowired
    private BusStopService busStopService;
    
    @GetMapping("/busStop")
    public List<BusStop> getStopsByLine(@RequestParam("lineId") int lineId) {
        return busStopService.getStopsByLine(lineId);
    }

}
