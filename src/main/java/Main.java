import bot.Bot;
import controller.UserController;
import factory.BotCreator;
import interactor.CreateUserInteractor;
import receive.EventCallback;
import ui.BotController;
import userEvent.Event;
import userEvent.MessageEvent;

public class Main {
    public static void main(String[] args) {
        Bot bot = new BotCreator().create();
        UserController userController = new UserController();
        CreateUserInteractor createUserInteractor = new CreateUserInteractor(userController);
        BotController botController = new BotController(bot, createUserInteractor);
        botController.start();
    }
}
