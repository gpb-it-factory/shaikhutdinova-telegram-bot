package factory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import receive.EventCallback;
import receive.EventSubscriber;
import userEvent.Event;

import java.util.List;

 class TelegramEventReceiver implements EventSubscriber<Event> {

    private final TelegramBot telegramBot;

    public TelegramEventReceiver(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void subscribe(EventCallback<Event> callback) {
        // Установка слушателя обновлений TelegramBot.
        telegramBot.setUpdatesListener(new UpdatesListener() {
            @Override
            //Реализация метода process из интерфейса UpdatesListener, который принимает список объектов Update.
            public int process(List<Update> updates) {

                for (Update update : updates) {
                    if (update.message() != null) {
                        if (update.message().text() != null) {
                            Event messageEvent = new MessageEventImpl(update.message().text(), update.message().from().firstName(), update.message().from().lastName(), update.message().from().id(), update.message().chat().id());
                            callback.onNewEvent(messageEvent);
                        } else if (update.message().sticker() != null) {
                            Event stickerEvent = new StickerEventImpl(update.message().sticker().fileId(), update.message().sticker().width(), update.message().sticker().height(), update.message().sticker().isAnimated(), update.message().from().id(), update.message().from().firstName(), update.message().from().lastName(), update.message().sticker().type().toString(), update.message().chat().id());
                            callback.onNewEvent(stickerEvent);
                        }
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
