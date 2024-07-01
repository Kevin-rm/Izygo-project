package mg.motus.izygo;

import mg.motus.izygo.dto.ProfileReservationDTO;
import mg.motus.izygo.repository.NotificationRepository;
import mg.motus.izygo.service.DashboardService;
import mg.motus.izygo.service.ProfileUserService;

import java.util.List;

import javax.management.Notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);


        DashboardService dashboardService = configurableApplicationContext.getBean(DashboardService.class);
        System.out.println(dashboardService.profitThroughYears());

        NotificationRepository nr = configurableApplicationContext.getBean(NotificationRepository.class);
        System.out.println(nr.findAllByUserId(3L));
    }

}
