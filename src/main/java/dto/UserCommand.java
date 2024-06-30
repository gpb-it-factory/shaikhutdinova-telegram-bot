package dto;

public enum UserCommand {
    REGISTER("/register"),
    CREATEACCOUNT("/createaccount"),
    CURRENTBALANCE ("/currentbalance"),
    TRANSFER ("/transfer"),
    UNKNOWN("unknown");

    private final String command;

    UserCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}




