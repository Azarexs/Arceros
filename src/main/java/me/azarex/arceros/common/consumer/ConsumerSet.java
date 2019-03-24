package me.azarex.arceros.common.consumer;

import java.util.Set;
import java.util.function.Consumer;

public interface ConsumerSet<T> {

    /**
     * @return Set of Consumers of type T
     */
    Set<Consumer<T>> consumers();

    /**
     * Adds a consumer to the set
     * @param consumer Consumer to be added to the {@code HashSet}
     */
    default void addAction(Consumer<T> consumer) {
        consumers().add(consumer);
    }

    /**
     * Calls a #forEach on the Set, and then accepts a value, denoted by the parameter.
     * @param value Value to be accepted by all of the consumers
     */
    default void executeOn(T value) {
        consumers().forEach(consumer -> consumer.accept(value));
    }

}
