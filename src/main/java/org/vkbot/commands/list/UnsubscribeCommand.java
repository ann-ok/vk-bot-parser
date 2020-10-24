package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;

public class UnsubscribeCommand extends Command {

    public UnsubscribeCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim().split(" ")[0].toLowerCase().equals("отписаться");
    }

    @Override
    public void exec(Message message) {
//        var user = App.userService.getUser(message.getFromId());
//        user.setAllNews(false);
//        var tags = SubscribeCommand.getTags(message.getText()
//                .replace("отписаться", "").trim());
//        if (tags.size() == 0) user.getTags().clear();
//        for (String s : tags) {
//            var tag = App.tagService.findTagInsensitive(s);
//            if (tag != null) user.getTags().remove(tag);
//        }
//        App.userService.updateUser(user);
//        Messenger.sendMessage("Ваши подписки успешно обновлены", message.getFromId());
    }

    @Override
    public String getAnswer(Message message) {
        return null;
    }
}