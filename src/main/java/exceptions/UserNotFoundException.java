package exceptions;

public class UserNotFoundException extends Exception{
    private  final String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
