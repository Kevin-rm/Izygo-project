package mg.motus.izygo;

import mg.motus.izygo.service.UserService;
import mg.motus.izygo.model.User;
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

        // Exemple d'utilisation de la fonction inscription
        Optional<User> newUser = userService.inscription("Rakoto", "jean", "123456", "passwo", (short) 1);
        newUser.ifPresentOrElse(
            u -> System.out.println("User registered: " + u),
            () -> System.out.println("User already exists")
        );

        // Exemple d'utilisation de la fonction checkLogin
        // boolean loginSuccess = userService.checkLogin("123456789", "password123");
        // System.out.println("Login success: " + loginSuccess);

        // Exemple d'utilisation de la fonction connexion
        // Optional<User> user = userService.connexion("123456789", "password123");
        // user.ifPresentOrElse(
        //     u -> System.out.println("User connected: " + u),
        //     () -> System.out.println("Invalid login")
        // );
    }
}
