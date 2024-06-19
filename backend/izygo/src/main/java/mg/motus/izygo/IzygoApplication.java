package mg.motus.izygo;

import mg.motus.izygo.dto.RouteDTO;
import mg.motus.izygo.model.Cancellation;
import mg.motus.izygo.service.CancellationService;
import mg.motus.izygo.service.ResearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        CancellationService cancellationService = configurableApplicationContext.getBean(CancellationService.class);

        Cancellation cancellation = cancellationService.cancelReservation(2L);
    }

}
