package org.vkbot.commands.list;

import com.vk.api.sdk.objects.messages.Message;

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
        // TODO изменить текст
        String msg = "Данный бот позволяет вам подписаться на новостную рассылку портала PROГОРОД - progorod43.ru\n" +
                "\nДоступные команды:\n" +
                "\n[Приветствие] - бот, приветствует вас в ответ;\n" +
                "\n[Список] - показывает теги, на которые вы подписаны, а также доступные для подписки;\n" +
                "\n[Подписаться] - подписывает вас на определенный тег или список тегов, заданных через пробел. " +
                "Для подписки на тег, состоящий из нескольких слов, напишите его в двойных кавычках. " +
                "Отсутвие тегов после ключевого слова подписывает вас на все теги;\n" +
                "\n[Отписаться] - отписывает вас от определенного тега или списка тегов, заданных через пробел. " +
                "Для отписки от тега, состоящего из нескольких слов, напишите его в двойных кавычках. " +
                "Отсутвие тегов после ключевого слова отписывает вас от всех сразу;\n";
        return msg;
    }

    @Override
    public void exec(Message message) {}
}
