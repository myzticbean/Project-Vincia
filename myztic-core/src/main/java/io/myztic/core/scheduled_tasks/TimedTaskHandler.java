package io.myztic.core.scheduled_tasks;

import io.myztic.core.MyzticCore;
import io.myztic.core.exceptions.UtilityClassException;
import io.myztic.core.logging.LogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class TimedTaskHandler {

    private static final BlockingQueue<Consumer<Object>> fiveSecTaskQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Consumer<Object>> thirtySecTaskQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Consumer<Object>> fiveMinTaskQueue = new LinkedBlockingQueue<>();

    private TimedTaskHandler() { throw new UtilityClassException(); }

    public static void runTaskEvery5Seconds() {
        try {
            if(!fiveSecTaskQueue.isEmpty())
                fiveSecTaskQueue.take().accept(null);
        } catch (InterruptedException ignored) { }
    }

    public static void runTaskEvery30Seconds() {
        try {
            if(!thirtySecTaskQueue.isEmpty())
                thirtySecTaskQueue.take().accept(null);
        } catch (InterruptedException ignored) { }
    }

    public static void runTaskEvery5Minutes() {
        try {
            if(!fiveMinTaskQueue.isEmpty())
                fiveMinTaskQueue.take().accept(null);
        } catch (InterruptedException ignored) { }
    }

    /**
     * Add a function to be executed in the 5 Sec queue
     * @param function
     */
    public static void addTaskTo5SecTaskQueue(Consumer<Object> function) {
        fiveSecTaskQueue.add(function);
    }

    /**
     * Add a function to be executed in the 30 Sec queue
     * @param function
     */
    public static void addTaskTo30SecTaskQueue(Consumer<Object> function) {
        fiveSecTaskQueue.add(function);
    }

    /**
     * Add a function to be executed in the 5 Min queue
     * @param function
     */
    public static void addTaskTo5MinTaskQueue(Consumer<Object> function) {
        fiveSecTaskQueue.add(function);
    }

    public static void addTaskTo5SecTaskQueue() {

        // @TODO: for testing, to be removed later
        addTaskTo5SecTaskQueue(arg -> {
            arg = "Hey";
            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 1 " + arg);
        });
        addTaskTo5SecTaskQueue(arg -> {
            arg = "Hey";
            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 2 " + arg);
        });
        addTaskTo5SecTaskQueue(arg -> {
            arg = "Hey";
            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 3 " + arg);
        });
        addTaskTo5SecTaskQueue(arg -> {
            arg = "Hey";
            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 4 " + arg);
        });
        addTaskTo5SecTaskQueue(arg -> {
            arg = "Hey";
            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 5 " + arg);
        });
    }
}
