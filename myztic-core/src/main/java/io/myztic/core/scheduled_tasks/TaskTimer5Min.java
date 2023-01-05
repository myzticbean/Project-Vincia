package io.myztic.core.scheduled_tasks;

public final class TaskTimer5Min implements Runnable {

    // implement a queue system,
    // whenever below method triggers it picks up a method from the queue and starts executing

    @Override
    public void run() {
        TimedTaskHandler.runTaskEvery5Minutes();
    }
}
