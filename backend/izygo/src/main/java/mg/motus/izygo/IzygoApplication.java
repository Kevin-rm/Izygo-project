package mg.motus.izygo;

import mg.motus.izygo.repository.BusLineRepository;
import mg.motus.izygo.repository.NotificationRepository;
import mg.motus.izygo.service.BusLineService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

      //  ResearchService researchService = configurableApplicationContext.getBean(ResearchService.class);
     //   ResearchRepository researchRepository = configurableApplicationContext.getBean(ResearchRepository.class);
     //   NotificationRepository notificationRepository = configurableApplicationContext.getBean(NotificationRepository.class);

    //    System.out.println(notificationRepository.findAllByUserId(1L));
    }

}
