package exceptions;public class AccountExistException extends  Exception {

    private  final String message;

    public AccountExistException(String message) {
        super(message);
        this.message = message;
    }
}
