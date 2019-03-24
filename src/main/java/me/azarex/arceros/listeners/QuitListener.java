package me.azarex.arceros.listeners;

import me.azarex.arceros.common.consumer.ConsumerSet;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class QuitListener extends ListenerAdapter implements ConsumerSet<GuildMemberLeaveEvent> {

    private Set<Consumer<GuildMemberLeaveEvent>> actions = new HashSet<>();

    public QuitListener() {

    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        executeOn(event);
    }

    @Override
    public Set<Consumer<GuildMemberLeaveEvent>> consumers() {
        return actions;
    }
}
