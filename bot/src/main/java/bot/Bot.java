package bot;

import common.Callback;
import receive.EventCallback;
import userEvent.Event;

public interface Bot {

    void sendMessage(long chatId, String message, Callback callback);

    void sendMessage(long chatId, String message);

    void subscribeToNewEvents(EventCallback<Event> callback);
}
