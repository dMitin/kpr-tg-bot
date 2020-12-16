package ru.kpr.tgbot.domain;

import kpr.bot.jooq.gen.tables.records.TgWarningRecord;

import java.time.LocalDateTime;

public class TgWarning {
    Integer id;
    LocalDateTime warningDate;
    Integer createdBy;
    Integer warnedTgUser;

    public static TgWarning create(TgWarningRecord wrnRcrd) {
        TgWarning tgWarning = new TgWarning();
        tgWarning.setId(wrnRcrd.getId());
        tgWarning.setWarningDate(wrnRcrd.getWarningTime());
        tgWarning.setWarnedTgUser(wrnRcrd.getWarnedTgUser());
        return tgWarning;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getWarningDate() {
        return warningDate;
    }

    public void setWarningDate(LocalDateTime warningDate) {
        this.warningDate = warningDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getWarnedTgUser() {
        return warnedTgUser;
    }

    public void setWarnedTgUser(Integer warnedTgUser) {
        this.warnedTgUser = warnedTgUser;
    }

    @Override
    public String toString() {
        return "TgWarning{" +
                "id=" + id +
                ", warningDate=" + warningDate +
                ", createdBy=" + createdBy +
                ", warnedTgUser=" + warnedTgUser +
                '}';
    }
}
