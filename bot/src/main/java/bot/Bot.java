package bot;

import common.Callback;
import receive.EventCallback;
import userEvent.Event;

public interface Bot {

    void sendMessage(long chatId, String message, Callback callback);

    void sendImage(long chatId, String photo, String caption, String selfText, Callback callback);

    void subscribeToNewEvents(EventCallback<Event> callback);
}
