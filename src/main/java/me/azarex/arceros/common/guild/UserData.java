package me.azarex.arceros.common.guild;

import java.util.concurrent.CompletableFuture;

public interface UserData {

    /**
     * @return {@code CompletableFuture<Void>} for the purpose of returning
     * the value asynchronously, and also getting whenever it's done.
     */
    CompletableFuture<Long> totalExperience();

    /**
     * @param exp Experience to be set on the user
     * @return {@code CompletableFuture<Void>} for the purpose of
     * getting whenever the action is complete
     */
    CompletableFuture<Void> setExperience(long exp);

    /**
     * @return {@code CompletableFuture<Long>} for the purpose of returning
     * the value asynchronously, and also getting whenever it's done.
     */
    CompletableFuture<Long> coins();

    /**
     * @param coins Coins to be set on the user
     * @return {@code CompletableFuture<Void>} for the
     * purpose of getting whenever the action is done
     */
    CompletableFuture<Void> setCoins(long coins);
}
