package ru.kpr.tgbot;

import kpr.bot.jooq.gen.Tables.*;
import kpr.bot.jooq.gen.tables.records.TgUserRecord;
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
import ru.kpr.tgbot.utils.TgUser;



import java.sql.Connection;

import static kpr.bot.jooq.gen.Tables.TG_USER;

public class Bot extends TelegramLongPollingCommandBot {

    public Bot () {
        register(new HelloCommand());
        register(new BanCommand());
    }

    public String getBotUsername() {
        return "myamazingbot";
    }

    public void processNonCommandUpdate(Update update) {

        Long chatId = update.getMessage().getChatId();
        User user = update.getMessage().getFrom();
        TgUser tgUser = TgUser.create(user);


        try (Connection con = DataSource.getConnection()) {
            DSLContext context = DSL.using(con, SQLDialect.POSTGRES);
            //TgUserRecord tgUserRecord = context.newRecord(TG_USER, tgUser);

            context.insertInto(TG_USER)
                    .set(TG_USER.USERNAME, tgUser.getUsername())
                    .set(TG_USER.FIRST_NAME, tgUser.getFirstName())
                    .set(TG_USER.LAST_NAME, tgUser.getLastName())
                    .onDuplicateKeyIgnore()
                    .execute();

            //tgUserRecord.store();
        }
        catch (Exception e) {
            SendMessage answer = new SendMessage();
            answer.setText(e.getMessage());
            answer.setChatId(chatId.toString());
            try {
                execute(answer); // Call method to send the message
            } catch (TelegramApiException e2) {
                e2.printStackTrace();
            }
        }



        SendMessage answer = new SendMessage();
        answer.setText("добавил пользователя");
        answer.setChatId(chatId.toString());
        try {
            execute(answer); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


    }

    public String getBotToken() {
        return "1403869531:AAFjrC3CBRUhuv6BcqkIXP5TrRN6aIXJJiA";
    }

    public void onRegister() {

    }


    /*public void onUpdatesReceived(List<Update> updates) {

    }*/




}
