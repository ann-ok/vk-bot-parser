package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.utils.Messenger;
import org.vkbot.utils.OperationResult;

public class GreetingCommand extends Command
{
    public GreetingCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        var greetWords = new String[]{
                "привет", "приветики", "приветули", "при", "приветствую",
                "здравствуй", "здравствуйте", "здарова",
                "салют",
                "hello", "hi",
                "ку", "qq", "q"
        };

        for (var word : message.toLowerCase().replaceAll("[,.]", "").split(" ")) {
            for (var greet : greetWords)
                if (word.equals(greet)) return true;
        }

        return false;
    }

    @Override
    public String getAnswer(Message message) {
        return "Привет, " + Messenger.getUserFirstName(message) + "\n\n(Для справки напишите \"Помощь\")";
    }

    @Override
    public OperationResult exec(Message message) {
        return new OperationResult();
    }
}
