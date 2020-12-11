package ru.kpr.tgbot.commands;

import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.kpr.tgbot.utils.Resources;
import ru.kpr.tgbot.utils.Services;
import ru.kpr.tgbot.utils.Utils;

public class UnbanCommand extends BotCommand {

    final String BESEDKA_CHAT = "-1001405844472";

    public UnbanCommand() {
        super("unBan", "Разрешить участнику писать в чат");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        try {
            unBan(absSender, user, chat, arguments);
        } catch (TelegramApiException e) {
            //BotLogger.error(LOGTAG, e);
            e.printStackTrace();
        }
    }


    public void unBan(AbsSender absSender, User user, Chat chat, String[] arguments) throws TelegramApiException {

        Long chatId = chat.getId();
        if (arguments == null || arguments.length != 1) {
            Utils.sendMessage(Resources.USERNAME_IS_REQUIRED, chatId.toString(), absSender);
            return;
        }

        String username = arguments[0];
        TgUserRecord tgUserRecord = null;
        try {
            tgUserRecord = Services.getTgUserByUsername(username);
        }
        catch (Exception e) {
            Utils.sendMessage(e.getMessage(), chatId.toString(), absSender);
        }

        RestrictChatMember restrictChatMember = Utils.getRestriction(tgUserRecord.getTgId(), BESEDKA_CHAT, true);
        absSender.execute(restrictChatMember);

    }
}
