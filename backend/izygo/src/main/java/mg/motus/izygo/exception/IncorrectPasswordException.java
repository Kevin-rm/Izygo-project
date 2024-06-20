package mg.motus.izygo.exception;

public class IncorrectPasswordException extends RuntimeException {
    public IncorrectPasswordException() {
        super("Mot de passe incorrect");
    }
}
