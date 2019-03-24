package me.azarex.arceros.database.impl;

import com.zaxxer.hikari.HikariDataSource;
import me.azarex.arceros.common.guild.ChannelData;
import me.azarex.arceros.common.guild.GuildData;
import me.azarex.arceros.common.guild.UserData;
import me.azarex.arceros.database.Database;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MySQL implements Database, GuildData {

    private final HikariDataSource hikari;
    private final RowSetFactory rowSetFactory;

    /* Variables for UserData queries */
    private final String retrieveCoins;
    private final String retrieveExp;
    private final String setCoins;
    private final String setExp;

    /* Variables for ChannelData queries */
    private final String retrieveLog;
    private final String retrieveBot;
    private final String setLog;
    private final String setBot;

    public MySQL(Map<String, Object> configuration) throws SQLException {
        hikari = new HikariDataSource();
        rowSetFactory = RowSetProvider.newFactory();

        retrieveCoins = configuration.get("retrieve_coins").toString();
        retrieveExp = configuration.get("retrieve_exp").toString();
        setCoins = configuration.get("set_coins").toString();
        setExp = configuration.get("set_exp").toString();

        retrieveLog = configuration.get("retrieve_log").toString();
        retrieveBot = configuration.get("retrieve_bot").toString();
        setLog = configuration.get("set_log").toString();
        setBot = configuration.get("set_bot").toString();

        hikari.addDataSourceProperty("serverName", configuration.get("serverName").toString());
        hikari.addDataSourceProperty("port", configuration.get("port").toString());
        hikari.addDataSourceProperty("databaseName", configuration.get("databaseName").toString());
        hikari.addDataSourceProperty("user", configuration.get("user").toString());
        hikari.addDataSourceProperty("password", configuration.get("password").toString());
    }

    @Override
    public HikariDataSource hikari() {
        return hikari;
    }

    @Override
    public RowSetFactory rowSetFactory() {
        return rowSetFactory;
    }

    @Override
    public UserData userData(Member member) {
        final long guildId = member.getGuild().getIdLong();
        final long userId = member.getUser().getIdLong();

        return new UserData() {
            @Override
            public CompletableFuture<Long> totalExperience() {
                return apply(String.format(retrieveExp, userId), 1);
            }

            @Override
            public CompletableFuture<Void> setExperience(long exp) {
                return update(setExp);
            }

            @Override
            public CompletableFuture<Long> coins() {
                return apply(String.format(retrieveCoins, userId), 1);
            }

            @Override
            public CompletableFuture<Void> setCoins(long coins) {
                return update(setCoins);
            }
        };
    }

    @Override
    public ChannelData channelData(long guildId) {
        return new ChannelData() {
            @Override
            public CompletableFuture<Long> joinLeaveChannel() {
                return apply(String.format(retrieveLog, guildId), 1);
            }

            @Override
            public CompletableFuture<Void> joinLeaveChannel(long channelId) {
                return update(String.format(setLog, channelId));
            }
        };
    }
}
