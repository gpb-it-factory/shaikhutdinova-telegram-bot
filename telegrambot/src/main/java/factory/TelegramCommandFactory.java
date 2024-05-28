package factory;

import com.pengrad.telegrambot.TelegramBot;
import common.Command;

class TelegramCommandFactory extends CommandFactory {
    private final TelegramBot telegramBot;

    public TelegramCommandFactory(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public Command getSendImageCommand(long chatId, String photo, String caption, String selfText) {
        return new SendImageCommand(telegramBot, chatId, photo, caption, selfText);
    }

    @Override
    public Command getSendMessageCommand(long chatId, String message) {
        return new SendMessageCommand(telegramBot, chatId, message);
    }
}
