package factory;

import common.Command;

abstract class CommandFactory {
    public abstract Command getSendMessageCommand(long chatId, String message);
}
