package dto;

public class RegisterUserRequest {
    private long userId;
    private String userName;

    public RegisterUserRequest(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
