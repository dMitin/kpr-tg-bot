package ru.kpr.tgbot.commands;

import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
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
import ru.kpr.tgbot.domain.TgBan;
import ru.kpr.tgbot.domain.TgUser;
import ru.kpr.tgbot.utils.DataSource;
import ru.kpr.tgbot.utils.Resources;
import ru.kpr.tgbot.utils.Services;
import ru.kpr.tgbot.utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

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


        //TgUserRecord bannedTgUser = null;
        Integer bannedById = null;
        TgUser bannedTgUser = null;
        try {
            //bannedTgUser = Services.getTgUserByUsername(username);
            TgUserRecord bannedBy = Services.getTgUserByUsername(user.getUserName());
            bannedTgUser = Services.getTgUserAllByUsername(username);

            if (bannedBy == null) {
                TgUser tgUser = TgUser.create(user);
                bannedById = Services.createTgUser(tgUser);
            } else {
                bannedById = bannedBy.getId();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            Utils.sendMessage(e.getMessage(), chatId.toString(), absSender);
        }

        System.out.println(bannedTgUser);


        if (bannedTgUser == null) {
            Utils.sendMessage("Не могу найти пользователя " + username,
                    chatId.toString(), absSender);
            return;
        }

        if (bannedTgUser.getBan() != null) {
            Utils.sendMessage("Пользователь " + username + " уже забанен",
                    chatId.toString(), absSender);
            return;
        }


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

        TgBan tgBan = new TgBan();
        tgBan.setCreatedBy(bannedById);
        tgBan.setBanTime(LocalDateTime.now());
        tgBan.setTgUserId(bannedTgUser.getId());
        try {
            Services.banTgUser(tgBan);
        }
        catch (Exception e) {
            e.printStackTrace();
            Utils.sendMessage(e.getMessage(), chatId.toString(), absSender);
        }





    }
}
