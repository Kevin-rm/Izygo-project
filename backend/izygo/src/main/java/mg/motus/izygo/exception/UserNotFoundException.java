package mg.motus.izygo.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Utilisateur non trouvé");
    }
}
