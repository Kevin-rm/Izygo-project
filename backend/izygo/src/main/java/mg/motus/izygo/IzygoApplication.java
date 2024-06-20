package mg.motus.izygo;

import mg.motus.izygo.model.User;
import mg.motus.izygo.repository.BusLineRepository;
import mg.motus.izygo.repository.UserRepository;
import mg.motus.izygo.service.BusLineService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class IzygoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(IzygoApplication.class, args);

        //341610025
        UserRepository userRepository = configurableApplicationContext.getBean(UserRepository.class);

        System.out.println(userRepository.findByPhoneNumber("341610025"));
    }

}
