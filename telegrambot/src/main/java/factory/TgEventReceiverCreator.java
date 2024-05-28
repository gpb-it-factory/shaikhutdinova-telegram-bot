package factory;

import com.pengrad.telegrambot.TelegramBot;
import common.Creator;

class TgEventReceiverCreator implements Creator<TelegramEventReceiverFactory> {
    private final TelegramBot telegramBot;

    public TgEventReceiverCreator(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public TelegramEventReceiverFactory create() {
        return new TelegramEventReceiverFactory(telegramBot);
    }
}
