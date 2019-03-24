package me.azarex.arceros.common.guild;

import java.util.concurrent.CompletableFuture;

public interface ChannelData {

    /**
     * @return {@code CompletableFuture<Long>} for the purpose of getting
     * the id of the channel, and knowing when it's done
     */
    CompletableFuture<Long> joinLeaveChannel();

    /**
     *
     * @param channelId Id of the channel, to be updated in the database
     * @return {@CompletableFuture<Void>} for the purpose of knowing
     * when the future is done executing
     */
    CompletableFuture<Void> joinLeaveChannel(long channelId);

}
