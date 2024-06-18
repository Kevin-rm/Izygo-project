package mg.motus.izygo;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.repository.ResearchRepository;
import mg.motus.izygo.service.ResearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Timestamp;
import java.util.List;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        ResearchService researchService = configurableApplicationContext.getBean(ResearchService.class);
        List<List<RouteDTO>> s = researchService.findRoute(1, 15);
        // List<RouteDTO> route = s.get(0);

        ResearchRepository researchRepository = configurableApplicationContext.getBean(ResearchRepository.class);

        System.out.println(researchRepository.findFutureArrivingBuses(
            12,
            Timestamp.valueOf("2024-06-16 08:05:00"),
            Timestamp.valueOf("2024-06-16 08:25:00"),
            "25 seconds"
            )
        );
    }

}
