import bot.Bot;
import factory.BotCreator;
import receive.EventCallback;
import userEvent.Event;
import userEvent.MessageEvent;

public class Main {
    public static void main(String[] args) {
        Bot bot = new BotCreator().create();
        bot.subscribeToNewEvents(new EventCallback<Event>() {
            @Override
            public void onNewEvent(Event userEvent) {
                if (userEvent instanceof MessageEvent) {
                    ((MessageEvent) userEvent).getMessage();
                    String responseMessage =
                            ((MessageEvent) userEvent).getMessage().equals("/ping") ? "pong" : "Это не пинг, чувак.";

                    bot.sendMessage(userEvent.getChatId(), responseMessage, new common.Callback() {

                        @Override
                        public void onSuccess() {
                            System.out.println("Сообщение отправлено: " + responseMessage);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }
}
