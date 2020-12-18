package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.utils.OperationResult;

public abstract class Command
{
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public abstract OperationResult exec(Message message);

    public abstract String getAnswer(Message message);

    public boolean check(String message) {
        return name.equals(message.split(" ")[0]);
    }

    public String getName() {
        return name;
    }
}