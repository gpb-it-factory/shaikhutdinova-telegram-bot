package factory;

import userEvent.MessageEvent;

class MessageEventImpl implements MessageEvent {
    private final String message;
    private final String firstName;
    private String lastName;
    private final Long userId;
    private final Long chatId;
    private final String userName;

    public MessageEventImpl(String message, String firstName, String lastName, Long userId, Long chatId, String userName) {
        this.message = message;
        this.userId = userId;
        this.firstName = firstName;
        this.chatId = chatId;
        this.userName = userName;

    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public Long getChatId() {
        return chatId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "MessageEventImpl{" +
                "message='" + message + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId=" + userId +
                ", chatId=" + chatId +
                ", userName='" + userName + '\'' +
                '}';
    }
}
