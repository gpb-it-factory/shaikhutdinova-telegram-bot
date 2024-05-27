package factory;

import common.Command;

abstract class CommandFactory {
    public abstract Command getSendImageCommand(long chatId, String photo, String caption, String selfText);
    public abstract Command getSendMessageCommand(long chatId, String message);
}