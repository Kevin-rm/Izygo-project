package mg.motus.izygo;

import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.BusLineRepository;
import mg.motus.izygo.service.BusLineService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        // ResearchService researchService = configurableApplicationContext.getBean(ResearchService.class);
        // List<List<RouteDTO>> s = researchService.findRoute(1, 15);
        // List<RouteDTO> route = s.get(0);

        // System.out.println(s);
    }

}
