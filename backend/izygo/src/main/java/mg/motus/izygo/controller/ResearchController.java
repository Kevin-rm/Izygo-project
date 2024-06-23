package mg.motus.izygo.controller;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.service.ResearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/search", method = RequestMethod.POST)
public class ResearchController {
    private final ResearchService researchService;

    public ResearchController(ResearchService researchService) {
        this.researchService = researchService;
    }

    @RequestMapping("/find-route")
    public List<RouteDTO> findRoute(@RequestBody Map<String, Integer> data) {
        return researchService.findRoute(
            data.get("departureStopId"),
            data.get("arrivalStopId")
        );
    }
}
