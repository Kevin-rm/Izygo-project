package mg.motus.izygo.controller;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.service.ResearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/search", method = RequestMethod.POST)
public class ResearchController {
    private final ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @RequestMapping("/find-route")
    public List<List<RouteDTO>> findRoute(int departureStopId, int arrivalStopId) {
        return researchService.findRoute(departureStopId, arrivalStopId);
    }
}
