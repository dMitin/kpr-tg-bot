package ru.kpr.tgbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;

public class Bot2 extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {


        //System.out.println(update.);
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            /*System.out.println(chatId);
            System.out.println("is channel:" + update.getMessage().isChannelMessage());
            System.out.println("is group:" + update.getMessage().isGroupMessage());
            System.out.println("is super group:" + update.getMessage().isSuperGroupMessage());
            System.out.println("is command:" + update.getMessage().isCommand());*/
            System.out.println("is reply:" + update.getMessage().isReply());
            System.out.println("authorSignature:" + update.getMessage().getAuthorSignature());
            System.out.println("forwardSignature:" + update.getMessage().getForwardSignature());
            System.out.println("forwardfrom:" + update.getMessage().getForwardFrom());

            SendMessage answer = new SendMessage();
            answer.setText("text");
            answer.setChatId(chatId.toString());

            /*ForceReplyKeyboard forceReplyKeyboard = new ForceReplyKeyboard();
            forceReplyKeyboard.setSelective(true);

             */
            answer.setReplyMarkup(getMainMenuKeyboard("1"));

            answer.enableMarkdown(true);
            answer.setReplyToMessageId(update.getMessage().getMessageId());

            //RestrictChatMember




            try {
                execute(answer); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "myamazingbot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "1403869531:AAFjrC3CBRUhuv6BcqkIXP5TrRN6aIXJJiA";
    }



    private static ReplyKeyboardMarkup getMainMenuKeyboard(String language) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("1");
        keyboardFirstRow.add("2");

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }




        public static void main(String[] args) {

            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new Bot2());


            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

}
