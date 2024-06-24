package factory;

import userEvent.StickerEvent;

class StickerEventImpl implements StickerEvent {

    private final String fileId;
    private final Integer width;
    private final Integer height;
    private final Boolean isAnimated;
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String type;
    private final Long chatId;
    private final String userName;

    public StickerEventImpl(String fileId, Integer width, Integer height, Boolean isAnimated, Long userId, String firstName, String lastName, String type, Long chatId, String userName) {
        this.fileId = fileId;
        this.width = width;
        this.height = height;
        this.isAnimated = isAnimated;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.chatId = chatId;
        this.userName = userName;
    }

    public String getFile_id() {
        return fileId;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Boolean getIs_animated() {
        return isAnimated;
    }

    @Override
    public Long getUserId() {
        return userId;
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
    public Long getChatId() {
        return chatId;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
