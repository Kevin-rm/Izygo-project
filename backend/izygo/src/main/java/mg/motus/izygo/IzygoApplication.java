package mg.motus.izygo;

import mg.motus.izygo.repository.ResearchRepository;
import mg.motus.izygo.service.ResearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        ResearchService researchService = configurableApplicationContext.getBean(ResearchService.class);
        ResearchRepository researchRepository = configurableApplicationContext.getBean(ResearchRepository.class);
        System.out.println(researchService.findRoute(1, 3));
        System.out.println(researchRepository.fetchRouteData(1, 3));
    }

}
