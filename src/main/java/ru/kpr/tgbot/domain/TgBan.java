package ru.kpr.tgbot.domain;

import java.time.LocalDateTime;

public class TgBan {
    Integer tgUserId;
    Integer createdBy;
    LocalDateTime banTime;

    public Integer getTgUserId() {
        return tgUserId;
    }

    public void setTgUserId(Integer tgUserId) {
        this.tgUserId = tgUserId;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getBanTime() {
        return banTime;
    }

    public void setBanTime(LocalDateTime banTime) {
        this.banTime = banTime;
    }

    @Override
    public String toString() {
        return "TgBan{" +
                "tgUserId=" + tgUserId +
                ", createdBy=" + createdBy +
                ", banTime=" + banTime +
                '}';
    }
}
