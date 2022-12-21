package io.myztic.scheduled_tasks;

public final class TaskTimer5Min implements Runnable {
    @Override
    public void run() {
        TimedTaskHandler.runTaskEvery5Minutes();
    }
}
