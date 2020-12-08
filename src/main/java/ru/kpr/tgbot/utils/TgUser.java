package ru.kpr.tgbot.utils;

import org.telegram.telegrambots.meta.api.objects.User;

import java.math.BigInteger;

public class TgUser {
    int id;
    String username;
    Integer tgId;
    String firstName;
    String lastName;

    public static TgUser create(User user) {
        TgUser tgUser = new TgUser();
        tgUser.setTgId(user.getId());
        tgUser.setUsername(user.getUserName());
        tgUser.setFirstName(user.getFirstName());
        tgUser.setLastName(user.getLastName());
        return  tgUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getTgId() {
        return tgId;
    }

    public void setTgId(Integer tgId) {
        this.tgId = tgId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
