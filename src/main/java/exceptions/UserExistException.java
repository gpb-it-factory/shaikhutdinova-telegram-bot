package exceptions;

public class UserExistException extends Exception {
    private final String message;

    public UserExistException(String message) {
        super(message);
        this.message = message;
    }
}
