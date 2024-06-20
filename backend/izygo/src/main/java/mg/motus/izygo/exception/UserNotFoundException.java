package mg.motus.izygo.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Utilisateur non trouvé");
    }
}
