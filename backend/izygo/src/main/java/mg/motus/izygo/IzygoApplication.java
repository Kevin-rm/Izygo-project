package mg.motus.izygo;

import mg.motus.izygo.service.BusStopService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        BusStopService busStopService = configurableApplicationContext.getBean(BusStopService.class);

        System.out.println(busStopService.findAll());
    }

}
