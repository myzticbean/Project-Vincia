package io.myztic.scheduled_tasks;

public final class TaskTimer5Sec implements Runnable {
    @Override
    public void run() {
        TimedTaskHandler.runTaskEvery5Seconds();
    }
}
