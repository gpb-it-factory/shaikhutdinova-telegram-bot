package dto;

public enum UserCommand {
    REGISTER("/register"),
    CREATEACCOUNT("/createaccount"),
    CURRENTBALANCE ("/currentbalance"),
    UNKNOWN("unknown"); // Предположим, что у вас есть неизвестная команда

    private final String command;

    UserCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}




