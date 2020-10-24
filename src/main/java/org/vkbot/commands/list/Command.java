package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;

public abstract class Command
{
    private final String name;

    Command(String name) {
        this.name = name;
    }

    public abstract void exec(Message message);

    public abstract String getAnswer(Message message);

    public boolean check(String message) {
        return name.equals(message.split(" ")[0]);
    }

    @Override
    public String toString() {
        return String.format("name: %s", this.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command) {
            return name.equals(((Command) obj).name);
        }
        return false;
    }
}