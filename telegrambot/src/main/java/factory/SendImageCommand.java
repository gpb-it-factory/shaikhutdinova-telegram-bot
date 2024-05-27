package factory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import common.Callback;
import common.Command;

import java.io.IOException;

class SendImageCommand implements Command {

    private final TelegramBot telegramBot;
    private final long chatId;
    private final String photo;
    private final String caption;
    private final String selfText;

    public SendImageCommand(TelegramBot telegramBot, long chatId, String photo, String caption, String selfText) {
        this.telegramBot = telegramBot;
        this.chatId = chatId;
        this.photo = photo;
        this.caption = caption;
        this.selfText = selfText;
    }

    @Override
    public void execute(Callback callback) {
        SendPhoto sendPhotoRequest = new SendPhoto(chatId, photo).caption(caption);

        telegramBot.execute(sendPhotoRequest, new com.pengrad.telegrambot.Callback<SendPhoto, SendResponse>() {
            @Override
            public void onResponse(SendPhoto request, SendResponse response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(SendPhoto request, IOException e) {
                callback.onError(e);
            }
        });
    }
}
