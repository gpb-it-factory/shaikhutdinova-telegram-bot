package factory;

import bot.Bot;
import com.pengrad.telegrambot.TelegramBot;
import common.Creator;

public class BotCreator implements Creator<Bot> {

    @Override
    public Bot create() {
        String botToken = System.getenv("TELEGRAM_BOT_TOKEN");
        if (botToken == null) {
            throw new IllegalArgumentException("no token passed in env variable!");
        }
        TelegramBot telegramBot = new TelegramBot(botToken);
        CommandFactory commandFactory = new CommandFactoryCreator(telegramBot).create();
        EventFactory eventFactory = new TgEventReceiverCreator(telegramBot).create();
        return new BotImpl(commandFactory, eventFactory);
    }
}
