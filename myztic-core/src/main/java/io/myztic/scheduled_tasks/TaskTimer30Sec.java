package io.myztic.scheduled_tasks;

public final class TaskTimer30Sec implements Runnable {
    @Override
    public void run() {
        TimedTaskHandler.runTaskEvery30Seconds();
    }
}
