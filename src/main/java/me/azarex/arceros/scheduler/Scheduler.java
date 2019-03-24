package me.azarex.arceros.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    private final ExecutorService cached = Executors.newCachedThreadPool();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);


    /**
     * Runs an asynchronous task
     * @param runnable
     *        Represents a task
     */
    public void async(Runnable runnable) {
        cached.submit(runnable);
    }

    /**
     * Runs an asynchronous task, with a delay
     * @param runnable
     *        Represents a task
     * @param delay
     *        Represents a delay, with will be associated with the {@link TimeUnit}
     * @param unit
     *        Represents a unit, which {@param delay} will depend on.
     */
    public void asyncLater(Runnable runnable, long delay, TimeUnit unit) {
        executor.schedule(runnable, delay, unit);
    }

    /**
     * Schedules an repeating task, with delays in between each time it's ran.
     * @param runnable
     *        Represents the task
     * @param delay
     *        Represents a delay, will be associated with {@link TimeUnit}
     * @param unit
     *        Represents a unit, which {@param delay} will depend on.
     */
    public void repeatingTask(Runnable runnable, long delay, TimeUnit unit) {
        executor.scheduleAtFixedRate(runnable, 0L, delay, unit);
    }

    public ExecutorService executor() {
        return cached;
    }
}
