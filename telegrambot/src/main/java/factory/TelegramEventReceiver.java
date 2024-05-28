package factory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
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
                    Message message = update.message();
                    if (message != null) {
                        if (message.text() != null) {
                            Event messageEvent = new MessageEventImpl(
                                    message.text(),
                                    message.from().firstName(),
                                    message.from().lastName(),
                                    message.from().id(),
                                    message.chat().id()
                            );
                            callback.onNewEvent(messageEvent);
                        } else if (message.sticker() != null) {
                            Event stickerEvent = new StickerEventImpl(
                                    message.sticker().fileId(),
                                    message.sticker().width(),
                                    message.sticker().height(),
                                    message.sticker().isAnimated(),
                                    message.from().id(),
                                    message.from().firstName(),
                                    message.from().lastName(),
                                    message.sticker().type().toString(),
                                    message.chat().id()
                            );
                            callback.onNewEvent(stickerEvent);
                        }
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
}
