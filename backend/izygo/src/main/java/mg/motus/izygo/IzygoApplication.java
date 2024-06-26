package mg.motus.izygo;

import mg.motus.izygo.dto.ProfileReservationDTO;
import mg.motus.izygo.service.ProfileUserService;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);
    }

}
