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

    @Override
    public void sendImage(long chatId, String photo, String caption, String selfText, Callback callback) {
        commandFactory.getSendImageCommand(chatId, photo, caption, selfText).execute(callback);
    }

    @Override
    public void subscribeToNewEvents(EventCallback<Event> callback) {
        eventFactory.getTelegramEventReceiver().subscribe(callback);
    }
}
