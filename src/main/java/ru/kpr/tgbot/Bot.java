package ru.kpr.tgbot;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.kpr.tgbot.commands.*;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kpr.tgbot.utils.DataSource;
import ru.kpr.tgbot.domain.TgUser;
import ru.kpr.tgbot.utils.Services;


import java.sql.Connection;
import java.sql.SQLException;

import static kpr.bot.jooq.gen.Tables.TG_USER;

public class Bot extends TelegramLongPollingCommandBot {

    public Bot () {
        register(new HelloCommand());
        register(new BanCommand());
        register(new UnbanCommand());
        register(new AddAdminCommand());
    }

    public String getBotUsername() {
        return "kpr-bot";
    }

    public void processNonCommandUpdate(Update update) {

        try {
            addUserToDb(update);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void addUserToDb(Update update) throws TelegramApiException, SQLException {
        User user = update.getMessage().getFrom();
        TgUser tgUser = TgUser.create(user);
        Services.createTgUser(tgUser);
    }

    public String getBotToken() {
        return "1403869531:AAGu4zQT-a9-8Kxdb6F3VG2UTf_3uyvL1k0";
    }

    public void onRegister() {

    }


    /*public void onUpdatesReceived(List<Update> updates) {

    }*/




}
