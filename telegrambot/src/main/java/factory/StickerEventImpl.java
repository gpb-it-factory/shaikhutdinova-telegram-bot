package factory;

import userEvent.StickerEvent;

class StickerEventImpl implements StickerEvent {

    private final String file_id;
    private final Integer width;
    private final Integer height;
    private final Boolean is_animated;
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final String type;
    private final Long chatId;

    public StickerEventImpl(String file_id, Integer width, Integer height, Boolean is_animated, Long userId, String firstName, String lastName, String type, Long chatId) {
        this.file_id = file_id;
        this.width = width;
        this.height = height;
        this.is_animated = is_animated;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.chatId = chatId;
    }

    public String getFile_id() {
        return file_id;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Boolean getIs_animated() {
        return is_animated;
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

}
