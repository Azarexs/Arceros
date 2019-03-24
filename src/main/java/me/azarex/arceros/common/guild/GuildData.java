package me.azarex.arceros.common.guild;

import net.dv8tion.jda.core.entities.Member;

public interface GuildData {

    /**
     * @param member Member that you will be getting the data of.
     * @return {@code UserData} of the member.
     */
    UserData userData(Member member);

    /**
     * @param guildId Id of the guild
     * @return {@code ChannelData} of the Guild
     */
    ChannelData channelData(long guildId);
}
