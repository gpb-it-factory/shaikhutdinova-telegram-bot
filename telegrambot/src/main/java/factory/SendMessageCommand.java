package factory;

import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import common.Command;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.*;

class SendMessageCommand implements Command {
    private final TelegramBot telegramBot;
    private final long chatId;
    private final String message;

    public SendMessageCommand(TelegramBot telegramBot, long chatId, String message) {
        this.telegramBot = telegramBot;
        this.chatId = chatId;
        this.message = message;
    }

    @Override
    public void execute(common.Callback callback) {
        SendMessage request = new SendMessage(chatId, new String(message.getBytes(), UTF_8));

        telegramBot.execute(request, new Callback<SendMessage, SendResponse>() {
            @Override
            public void onResponse(SendMessage request, SendResponse response) {
                callback.onSuccess();
            }

            @Override
            public void onFailure(SendMessage request, IOException e) {
                callback.onError(e);
            }
        });
    }
}
