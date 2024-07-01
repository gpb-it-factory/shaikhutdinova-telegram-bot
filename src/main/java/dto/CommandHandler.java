package dto;

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
            case "/currentbalance"->{
                command=UserCommand.CURRENTBALANCE;
            }
             case "/transfer"->{
                 command=UserCommand.TRANSFER;
             }
            default ->  {
                command = UserCommand.UNKNOWN;
            }
        }
        return command;
    }
}
