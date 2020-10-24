package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;

public class ListCommand extends Command {

    public ListCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim().split(" ")[0].toLowerCase().equals("список");
    }

    @Override
    public void exec(Message message) {
//        var user = App.userService.getUser(message.getFromId());
//        new TagsNotification().exec(message.getFromId());
//        var sb = new StringBuilder();
//        var tags = App.tagService.findAllTags();
//        for (var tag : tags) {
//            if (user.isAllNews() || !user.getTags().contains(tag)) {
//                sb.append(tag.getName()).append(", ");
//            }
//        }
//        sb.replace(sb.lastIndexOf(","), sb.length(), "");
//        Messenger.sendMessage("Доступные для подписки теги:\n" + sb.toString(), message.getFromId());
    }

    @Override
    public String getAnswer(Message message) {
        return null;
    }
}
