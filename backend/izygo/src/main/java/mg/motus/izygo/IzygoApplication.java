package mg.motus.izygo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import mg.motus.izygo.model.User;
import mg.motus.izygo.service.UserService;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IzygoApplication {

	public static void registerUserTest(UserService userService){
        User user = User.builder()
                .firstname(null)
                .lastname("Doe")
                .phoneNumber("1234519846")
                .password("password")
                .roleId((short) 1)
                .build();

        User savedUser = userService.registerUser(user);

        if (savedUser != null) {
            System.out.println("Utilisateur enregistré avec succès : " + savedUser);
        } else {
            System.out.println("Erreur lors de l'enregistrement de l'utilisateur.");
        }
	}

	public static void checkLoginTest(UserService userService){
        User loggedInUser = userService.checkLogin("123456789", "password");

        if (loggedInUser != null) {
            System.out.println("Connexion réussie pour l'utilisateur : " + loggedInUser);
        } else {
            System.out.println("Échec de la connexion. Vérifiez les identifiants.");
        }
	}

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(IzygoApplication.class, args);
		UserService userService = context.getBean(UserService.class);

		registerUserTest(userService);
	}

}
