package mg.motus.izygo.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Mot de passe incorrect");
    }
}
