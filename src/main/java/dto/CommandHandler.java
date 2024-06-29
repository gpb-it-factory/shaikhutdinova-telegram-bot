package dto;

import com.pengrad.telegrambot.model.User;

public class CommandHandler {

    public UserCommand parseCommand(String input) {
        UserCommand command;
         switch (input) {
            case "/register" -> {
                 command = UserCommand.REGISTER;
            }
            case "/createaccount" -> {
                 command = UserCommand.CREATEACCOUNT;
            }
            default ->  {
                command = UserCommand.UNKNOWN;
            }
        }
        return command;
    }
}
