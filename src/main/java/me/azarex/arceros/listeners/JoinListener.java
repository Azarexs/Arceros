package me.azarex.arceros.listeners;

import me.azarex.arceros.common.consumer.ConsumerSet;
import me.azarex.arceros.common.guild.ChannelData;
import me.azarex.arceros.common.guild.GuildData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class JoinListener extends ListenerAdapter implements ConsumerSet<GuildMemberJoinEvent> {

    private final Set<Consumer<GuildMemberJoinEvent>> actions = new HashSet<>();

    public JoinListener(Map<String, Object> configuration, GuildData guildData) {
        addAction(event -> {
            ChannelData channelData = guildData.channelData(event.getGuild().getIdLong());

            channelData.joinLeaveChannel().whenCompleteAsync((channelId, throwable) -> {

                final TextChannel channel = event.getGuild().getTextChannelById(channelId);
                channel.sendMessage(joinEmbed(event.getUser()).build());
            });
        });
    }

    private EmbedBuilder joinEmbed(User user) {
        return new EmbedBuilder()
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setDescription(String.format("%s has joined the official discord server!", user.getName()));
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        executeOn(event);
    }

    @Override
    public Set<Consumer<GuildMemberJoinEvent>> consumers() {
        return actions;
    }
}
