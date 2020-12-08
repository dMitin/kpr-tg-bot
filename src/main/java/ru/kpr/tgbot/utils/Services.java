package ru.kpr.tgbot.utils;

import kpr.bot.jooq.gen.tables.records.TgUserRecord;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;

import static kpr.bot.jooq.gen.Tables.TG_USER;

public class Services {
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
}
