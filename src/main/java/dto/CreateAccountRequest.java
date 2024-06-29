package dto;

public class CreateAccountRequest {
    private long userId;

    public CreateAccountRequest(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "userId=" + userId +
                '}';
    }
}
