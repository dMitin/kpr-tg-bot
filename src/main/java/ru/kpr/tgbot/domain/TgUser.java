package ru.kpr.tgbot.domain;

import kpr.bot.jooq.gen.tables.records.TgBanRecord;
import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import kpr.bot.jooq.gen.tables.records.TgWarningRecord;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;
import java.util.stream.Collectors;

public class TgUser {
    int id;
    String username;
    Integer tgId;
    String firstName;
    String lastName;

    TgBan ban;
    List<TgWarning> warnings;

    public static TgUser create(User user) {
        TgUser tgUser = new TgUser();
        tgUser.setTgId(user.getId());
        tgUser.setUsername(user.getUserName());
        tgUser.setFirstName(user.getFirstName());
        tgUser.setLastName(user.getLastName());
        return  tgUser;
    }

    public static TgUser create(TgUserRecord tgUserRecord, TgBanRecord tgBanRecord, List<TgWarningRecord> tgWarningRecords) {
        TgUser tgUser = new TgUser();
        tgUser.setId(tgUserRecord.getId());
        tgUser.setTgId(tgUserRecord.getTgId());
        tgUser.setUsername(tgUserRecord.getUsername());
        tgUser.setFirstName(tgUserRecord.getFirstName());
        tgUser.setLastName(tgUserRecord.getLastName());

        if (tgBanRecord != null) {
            TgBan tgBan = new TgBan();
            tgBan.setTgUserId(tgBanRecord.getTgUserId());
            tgBan.setCreatedBy(tgBanRecord.getCreatedBy());
            tgBan.setBanTime(tgBanRecord.getBanTime());
            tgUser.setBan(tgBan);
        }


        if (tgWarningRecords != null) {
            List<TgWarning> warnings = tgWarningRecords.stream()
                    .map(TgWarning::create)
                    .collect(Collectors.toList());
            tgUser.setWarnings(warnings);
        }
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

    public TgBan getBan() {
        return ban;
    }

    public void setBan(TgBan ban) {
        this.ban = ban;
    }

    public List<TgWarning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<TgWarning> warnings) {
        this.warnings = warnings;
    }


    @Override
    public String toString() {
        return "TgUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", tgId=" + tgId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ban=" + ban +
                ", warnings=" + warnings +
                '}';
    }
}
