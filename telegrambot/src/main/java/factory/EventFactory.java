package factory;

import receive.EventSubscriber;
import userEvent.Event;

abstract class EventFactory {
    public abstract EventSubscriber<Event> getTelegramEventReceiver();
}
