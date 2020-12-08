package ru.kpr.tgbot.utils;

import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.ChatPermissions;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Utils {


    public static void sendMessage(String message, String chatId, AbsSender absSender) throws TelegramApiException {

        SendMessage answer = new SendMessage();
        answer.setText(message);
        answer.setChatId(chatId.toString());
        absSender.execute(answer);
    }

    public static RestrictChatMember getRestriction(Integer userId, String chatId, Boolean canSendMessages) {

        RestrictChatMember restrictChatMember = new RestrictChatMember();
        restrictChatMember.setChatId(chatId);
        restrictChatMember.setUserId(userId);
        ChatPermissions permissions = new ChatPermissions();
        permissions.setCanSendMessages(canSendMessages);
        restrictChatMember.setPermissions(permissions);
        return restrictChatMember;
    }

}
