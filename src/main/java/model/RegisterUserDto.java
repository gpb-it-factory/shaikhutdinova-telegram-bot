package model;

public class RegisterUserDto {
    private Long userId;
    private String userName;

    public RegisterUserDto(Long userId, String userName) {
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
