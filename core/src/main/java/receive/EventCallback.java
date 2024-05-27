package receive;

public interface EventCallback <T> {
    void onNewEvent(T event);
}
