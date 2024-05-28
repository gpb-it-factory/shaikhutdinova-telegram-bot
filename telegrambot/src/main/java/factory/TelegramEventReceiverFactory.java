package factory;

import com.pengrad.telegrambot.TelegramBot;
import receive.EventSubscriber;
import userEvent.Event;

class TelegramEventReceiverFactory extends EventFactory {

    private final TelegramBot telegramBot;

    public TelegramEventReceiverFactory(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public EventSubscriber<Event> getTelegramEventReceiver() {
        return new TelegramEventReceiver(telegramBot);
    }
}

