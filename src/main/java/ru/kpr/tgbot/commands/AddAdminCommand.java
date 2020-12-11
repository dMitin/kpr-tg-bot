package ru.kpr.tgbot.commands;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.kpr.tgbot.utils.DataSource;
import ru.kpr.tgbot.utils.Resources;
import ru.kpr.tgbot.utils.Utils;

import java.sql.Connection;

import static kpr.bot.jooq.gen.Tables.TG_USER;

public class AddAdminCommand extends BotCommand {

    public AddAdminCommand() {
        super("addadmin", "Добавить администратора в чат");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        try {
            addAdmin(absSender, user, chat, arguments);
        } catch (TelegramApiException e) {
            //BotLogger.error(LOGTAG, e);
            e.printStackTrace();
        }
    }


    public void addAdmin(AbsSender absSender, User user, Chat chat, String[] arguments) throws TelegramApiException {

        Long chatId = chat.getId();
        if (arguments == null || arguments.length != 1) {
            Utils.sendMessage(Resources.USERNAME_IS_REQUIRED, chatId.toString(), absSender);
            return;
        }

        String username = arguments[0];
        int updated = 0;
        try (Connection con = DataSource.getConnection()) {

            DSLContext context = DSL.using(con, SQLDialect.POSTGRES);
            updated = context.update(TG_USER)
                    .set(TG_USER.IS_ADMIN, true)
                    .where(TG_USER.USERNAME.eq(username))
                    .execute();
        }
        catch (Exception e) {
            Utils.sendMessage(e.getMessage(), chatId.toString(), absSender);
        }

        if (updated == 0) {
            Utils.sendMessage("Пользователь " + username + " не найден", chatId.toString(), absSender);
        } else {
            Utils.sendMessage("Пользователь " + username + " теперь администратор", chatId.toString(), absSender);
        }
    }
}

