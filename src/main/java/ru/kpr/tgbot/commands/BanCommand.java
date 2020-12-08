package ru.kpr.tgbot.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BanCommand extends BotCommand {

    final String ADMIN_CHAT = "-478307323";
    final String BESEDKA_CHAT = "-1001405844472";

    public BanCommand() {
        super("ban", "Запретить участнику писать в чат");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {



        System.out.println("Команда банить, чат " + chat.getId().toString());
        System.out.println("Супергруппа? " + chat.isSuperGroupChat());
        System.out.println("Юзер id " + user.getId());
        System.out.println("Юзер id " + user.getUserName());


        //if (arguments == null || arguments.length != 1) {
        if (false) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Нужно указать участника");
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                //BotLogger.error(LOGTAG, e);
            }
        } else {

            /*
            String bannedUser = arguments[0];
            String userName = chat.getUserName();
            if (userName == null || userName.isEmpty()) {
                userName = user.getFirstName() + " " + user.getLastName();
            }*/

            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Буду банить ");
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                System.out.println(e);
                //BotLogger.error(LOGTAG, e);
            }

            /*GetChatMember chatMember = new GetChatMember();
            chatMember.setChatId(BESEDKA_CHAT);
            chatMember.setUserId(bannedUser);

             */

            RestrictChatMember restrictChatMember = new RestrictChatMember();
            restrictChatMember.setChatId(BESEDKA_CHAT);
            restrictChatMember.setUserId(137227222);
            //restrictChatMember.setUserId(162372951);
            ChatPermissions permissions = new ChatPermissions();
            permissions.setCanSendMessages(false);
            restrictChatMember.setPermissions(permissions);

            try {
                absSender.execute(restrictChatMember);
                System.out.println(restrictChatMember);
            } catch (TelegramApiException e) {
                //BotLogger.error(LOGTAG, e);
                System.out.println(e);
            }
        }


        /*try {
            absSender.execute(restrictChatMember);
        } catch (TelegramApiException e) {
            //BotLogger.error(LOGTAG, e);
        }*/
    }
}
