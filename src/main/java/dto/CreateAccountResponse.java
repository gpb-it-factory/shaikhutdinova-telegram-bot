package dto;

public class CreateAccountResponse {
    private String message;



    public CreateAccountResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CreateAccountResponse() {

    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CreateAccountResponse{" +
                "message='" + message + '\'' +
                '}';
    }
}
