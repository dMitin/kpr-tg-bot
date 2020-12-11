package ru.kpr.tgbot.commands;

import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ru.kpr.tgbot.utils.DataSource;
import ru.kpr.tgbot.utils.Resources;
import ru.kpr.tgbot.utils.Services;
import ru.kpr.tgbot.utils.Utils;

import java.sql.Connection;

import static kpr.bot.jooq.gen.Tables.TG_USER;

public class BanCommand extends BotCommand {

    final String ADMIN_CHAT = "-478307323";
    final String BESEDKA_CHAT = "-1001405844472";

    public BanCommand() {
        super("ban", "Запретить участнику писать в чат");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        try {
            ban(absSender, user, chat, arguments);
        } catch (TelegramApiException e) {
            //BotLogger.error(LOGTAG, e);
            e.printStackTrace();
        }
    }


    public void ban(AbsSender absSender, User user, Chat chat, String[] arguments) throws TelegramApiException {

        Long chatId = chat.getId();

        if (arguments == null || arguments.length != 1) {
            Utils.sendMessage(Resources.USERNAME_IS_REQUIRED, chatId.toString(), absSender);
            return;
        }

        String username = arguments[0];


        TgUserRecord bannedTgUser = null;
        TgUserRecord requestedTgUser = null;
        try {
            requestedTgUser = Services.getTgUserByUsername(user.getUserName());
            bannedTgUser = Services.getTgUserByUsername(username);
        }
        catch (Exception e) {
            Utils.sendMessage(e.getMessage(), chatId.toString(), absSender);
        }

        System.out.println(bannedTgUser);

        if (!requestedTgUser.getIsAdmin()) {
            Utils.sendMessage("Пользователь " + user.getUserName() + " не является администратором",
                    chatId.toString(), absSender);
            return;
        }

        if (null != bannedTgUser) {
            RestrictChatMember restrictChatMember = Utils.getRestriction(
                    bannedTgUser.getTgId(), BESEDKA_CHAT, false);
            try {
                absSender.execute(restrictChatMember);
            } catch (TelegramApiRequestException e) {
                if (e.getApiResponse().equals("Bad Request: can't remove chat owner")) {
                    Utils.sendMessage("Не могу забанить создателя чата", chatId.toString(), absSender);
                } else {
                    throw e;
                }
            }
        } else {
            Utils.sendMessage("Не могу найти пользователя " + username,
                    chatId.toString(), absSender);
        }

    }
}
