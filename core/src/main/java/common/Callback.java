package common;

public interface Callback {
    void onSuccess();
    void onError(Throwable e);
}
