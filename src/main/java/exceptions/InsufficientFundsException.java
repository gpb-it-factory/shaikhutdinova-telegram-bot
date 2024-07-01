package exceptions;

public class InsufficientFundsException extends Exception{

    private final String meessage;

    public InsufficientFundsException(String meessage) {
       super(meessage);
        this.meessage = meessage;
    }
}
