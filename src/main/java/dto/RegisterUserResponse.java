package dto;


public class RegisterUserResponse {
    private long userId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterUserResponse{" +
                "userId=" + userId +
                '}';
    }
}
