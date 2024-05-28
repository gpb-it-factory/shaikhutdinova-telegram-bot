package receive;

public interface EventSubscriber <T> {
    void subscribe(EventCallback<T> callback);
}
