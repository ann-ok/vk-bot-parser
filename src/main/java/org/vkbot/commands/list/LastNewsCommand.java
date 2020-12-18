package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.App;
import org.vkbot.utils.NewsObserver;
import org.vkbot.utils.OperationResult;

public class LastNewsCommand extends Command {
    public LastNewsCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        return message.trim()
                .split(" ")[0]
                .toLowerCase()
                .equals("последние");
    }

    @Override
    public String getAnswer(Message message) {
        StringBuilder msg = new StringBuilder();

        var listNews = App.newsService.findDESCLimit(5);
        for (var news : listNews) {
            msg.append(NewsObserver.getNewsString(news));
            msg.append("\n\n");
        }

        return msg.toString();
    }

    @Override
    public OperationResult exec(Message message) {
        return new OperationResult();
    }
}
