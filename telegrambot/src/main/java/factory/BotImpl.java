package factory;

import bot.Bot;
import common.Callback;
import receive.EventCallback;
import userEvent.Event;

class BotImpl implements Bot {

    private final CommandFactory commandFactory;
    private final EventFactory eventFactory;

    public BotImpl(CommandFactory commandFactory, EventFactory eventFactory) {
        this.commandFactory = commandFactory;
        this.eventFactory = eventFactory;
    }

    @Override
    public void sendMessage(long chatId, String message, Callback callback) {
        commandFactory.getSendMessageCommand(chatId, message).execute(callback);
    }

    //синхронный вариант вызова [sendMessage]
    @Override
    public void sendMessage(long chatId, String message) {
        commandFactory.getSendMessageCommand(chatId, message).execute(new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void subscribeToNewEvents(EventCallback<Event> callback) {
        eventFactory.getTelegramEventReceiver().subscribe(callback);
    }
}
