import bot.Bot;
import factory.BotCreator;
import controller.BotController;

public class Main {
    public static void main(String[] args) {
        Bot bot = new BotCreator().create();
        BotController botController = new BotController(bot);
        botController.start();
    }
}
