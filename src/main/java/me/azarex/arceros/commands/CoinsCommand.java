package me.azarex.arceros.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import me.azarex.arceros.common.guild.GuildData;
import me.azarex.arceros.common.guild.UserData;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.concurrent.CompletableFuture;

public class CoinsCommand extends Command {

    private GuildData guildData;

    public CoinsCommand(GuildData guildData) {
        this.guildData = guildData;

        name = "coins";
        aliases = new String[] { "coin" };
    }

    @Override
    protected void execute(CommandEvent event) {
        final Member member = event.getMember();
        final TextChannel channel = event.getTextChannel();

        final UserData userData = guildData.userData(member);
        final CompletableFuture<Long> future = userData.coins();

        future.whenCompleteAsync((coins, throwable) -> event.reply("You have " + coins));
    }
}
