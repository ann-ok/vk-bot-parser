package org.vkbot.commands;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.commands.list.*;
import org.vkbot.utils.Messenger;

import java.util.HashSet;

public class CommandManager
{
    private static final HashSet<Command> commands = new HashSet<>();

    static {
        add(new UnknownCommand("unknown"));
        add(new GreetingCommand("hello"));
        add(new SubscribeCommand("subscribe"));
        add(new UnsubscribeCommand("unsubscribe"));
        add(new ListCommand("list"));
        add(new HelpCommand("help"));
    }

    public static void add(Command command) {
        commands.add(command);
    }

    public static void execute(Message message) {
        var command = getCommand(message);
        execCommand(command, message);
    }

    public static Command getCommand(Message message) {
        String messageText = message.getText();
        for (Command command : CommandManager.commands) {
            if (command.check(messageText)) return command;
        }
        return new UnknownCommand("unknown");
    }

    public static void execCommand(Command command, Message message) {
        command.exec(message); // Выполнение команды

        // Отправка ответа пользователю
        var textMsg = command.getAnswer(message);
        Messenger.sendMessage(textMsg, message.getFromId());
    }
}
