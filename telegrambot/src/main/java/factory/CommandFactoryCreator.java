package factory;

import com.pengrad.telegrambot.TelegramBot;
import common.Creator;

 class CommandFactoryCreator implements Creator<CommandFactory> {

    private final TelegramBot telegramBot;

    public CommandFactoryCreator(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public CommandFactory create() {
        return new TelegramCommandFactory(telegramBot);
    }
}
