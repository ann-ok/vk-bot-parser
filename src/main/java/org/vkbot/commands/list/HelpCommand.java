package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;
import org.vkbot.utils.OperationResult;

public class HelpCommand extends Command
{
    public HelpCommand(String name) {
        super(name);
    }

    @Override
    public boolean check(String message) {
        var helpWords = new String[]{
                "помоги", "помощь",
                "help", "h",
                "?"
        };

        for (var word : message.toLowerCase()
                .replaceAll("[,.]", "")
                .split(" ")) {
            for (var help : helpWords)
                if (word.equals(help)) return true;
        }

        return false;
    }

    @Override
    public String getAnswer(Message message) {
        String msg = "Данный бот позволяет вам подписаться на новостную рассылку сайта Кировского физико-математический лицея\n" +
                "\nДоступные команды:\n" +
                "\nСтатус - узнать статус подписки." +
                "\nПодписаться - подписывает вас на все новости." +
                "\nОтписаться - отписывает вас от всех новостей." +
                "\nПоследние - отправляет последние 5 новостей.";

        return msg;
    }

    @Override
    public OperationResult exec(Message message) {
        return new OperationResult();
    }
}
