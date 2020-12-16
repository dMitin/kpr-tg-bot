package ru.kpr.tgbot.utils;

import kpr.bot.jooq.gen.tables.TgWarning;
import kpr.bot.jooq.gen.tables.records.TgBanRecord;
import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import kpr.bot.jooq.gen.tables.records.TgWarningRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ru.kpr.tgbot.domain.TgBan;
import ru.kpr.tgbot.domain.TgUser;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static kpr.bot.jooq.gen.Tables.TG_USER;
import static kpr.bot.jooq.gen.Tables.TG_BAN;
import static kpr.bot.jooq.gen.Tables.TG_WARNING;

public class Services {
    /**
     * Получить только основную информацию по пользователю телеграмм
     * @param username
     * @return
     * @throws SQLException
     */
    public static TgUserRecord getTgUserByUsername(String username) throws SQLException {

        TgUserRecord tgUserRecord = null;
        try (Connection con = DataSource.getConnection()) {
            DSLContext jooq = DSL.using(con, SQLDialect.POSTGRES);
            tgUserRecord  = jooq.selectFrom(TG_USER)
                    .where(TG_USER.USERNAME.eq(username))
                    .fetchAny();
        }
        return tgUserRecord;
    }

    public static TgUser getTgUserAllByUsername(String username) throws SQLException {

        TgUserRecord tgUserRecord = null;
        TgBanRecord tgBanRecord = null;
        List<TgWarningRecord> warningRecordList = null;
        try (Connection con = DataSource.getConnection()) {
            DSLContext jooq = DSL.using(con, SQLDialect.POSTGRES);

            tgUserRecord  = jooq.selectFrom(TG_USER)
                    .where(TG_USER.USERNAME.eq(username))
                    .fetchAny();
            if (tgUserRecord == null) {
                return null;
            }

            tgBanRecord  = jooq.selectFrom(TG_BAN)
                    .where(TG_BAN.TG_USER_ID.eq(tgUserRecord.getId()))
                    .fetchAny();

            warningRecordList  = jooq.selectFrom(TG_WARNING)
                    .where(TG_WARNING.WARNED_TG_USER.eq(tgUserRecord.getId()))
                    .fetch();
        }

        TgUser tgUser = TgUser.create(tgUserRecord, tgBanRecord, warningRecordList);
        return tgUser;
    }

    public static void banTgUser(TgBan ban) throws SQLException {
        try (Connection con = DataSource.getConnection()) {
            DSLContext jooq = DSL.using(con, SQLDialect.POSTGRES);

            TgBanRecord banRecord = jooq.newRecord(TG_BAN, ban);
            banRecord.store();
        }
    }

    public static Integer createTgUser(TgUser tgUser) throws SQLException {

        Record1<Integer> tgId;
        try (Connection con = DataSource.getConnection()) {
            DSLContext context = DSL.using(con, SQLDialect.POSTGRES);
            tgId = context.insertInto(TG_USER)
                    .set(TG_USER.USERNAME, tgUser.getUsername())
                    .set(TG_USER.TG_ID, tgUser.getTgId())
                    .set(TG_USER.FIRST_NAME, tgUser.getFirstName())
                    .set(TG_USER.LAST_NAME, tgUser.getLastName())
                    .set(TG_USER.IS_ADMIN, false)
                    .onDuplicateKeyIgnore()
                    .returningResult(TG_USER.ID)
                    .fetchOne();
        }
        return tgId.value1();
    }
}
