package factory;

import userEvent.MessageEvent;

class MessageEventImpl implements MessageEvent {
    private final String message;
    private final String firstName;
    private String lastName;
    private final Long userId;
    private final Long chatId;

    public MessageEventImpl(String message, String firstName, String lastName, Long userId, Long chatId) {
        this.message = message;
        this.userId = userId;
        this.firstName = firstName;
        this.chatId = chatId;

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
    public String toString() {
        return "MessageEventImpl{" +
                "message='" + message + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId=" + userId +
                ", chatId=" + chatId +
                '}';
    }
}
