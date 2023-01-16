package io.myztic.core.scheduled_tasks;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.coreconfig.ConfigProvider;
import io.myztic.core.exceptions.UtilityClassException;
import io.myztic.core.logging.LogUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TimedTaskHandler {

    // implement a queue system,
    // whenever below method triggers it picks up a method from the queue and starts executing
    // Prev impl -> "private static final BlockingQueue<Consumer<Object>> fiveSecTaskQueue = new LinkedBlockingQueue<>()"
    private static final BlockingQueue<RunnableFunction> fiveSecTaskQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<RunnableFunction> thirtySecTaskQueue = new LinkedBlockingQueue<>();
    private static final BlockingQueue<RunnableFunction> fiveMinTaskQueue = new LinkedBlockingQueue<>();

    private static final String QUEUE_EMPTY_ERROR_MSG = "Queue is empty";

    private TimedTaskHandler() { throw new UtilityClassException(); }

    public static void runTaskEvery5Seconds() {
        try {
            LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 5 Sec interval task...", ConfigProvider.getInst().DEBUG_MODE);
            if(!fiveSecTaskQueue.isEmpty())
                fiveSecTaskQueue.take().run();
            else
                LogUtil.logDebugInfo(MyzticCore.getPrefix(), QUEUE_EMPTY_ERROR_MSG, ConfigProvider.getInst().DEBUG_MODE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtil.logError(MyzticCore.getPrefix(), "Error while running 5 sec task: ", e);
        }
    }

    public static void runTaskEvery30Seconds() {
        try {
            LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 30 Sec interval task...", ConfigProvider.getInst().DEBUG_MODE);
            if(!thirtySecTaskQueue.isEmpty())
                thirtySecTaskQueue.take().run();
            else
                LogUtil.logDebugInfo(MyzticCore.getPrefix(), QUEUE_EMPTY_ERROR_MSG, ConfigProvider.getInst().DEBUG_MODE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtil.logError(MyzticCore.getPrefix(), "Error while running 30 sec task: ", e);
        }
    }

    public static void runTaskEvery5Minutes() {
        try {
            LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 5 Min interval task...", ConfigProvider.getInst().DEBUG_MODE);
            if(!fiveMinTaskQueue.isEmpty())
                fiveMinTaskQueue.take().run();
            else
                LogUtil.logDebugInfo(MyzticCore.getPrefix(), QUEUE_EMPTY_ERROR_MSG, ConfigProvider.getInst().DEBUG_MODE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtil.logError(MyzticCore.getPrefix(), "Error while running 5 min task: ", e);
        }
    }

    /**
     * Add a function to be executed in the 5 Sec queue
     * @param function Function to run
     */
    public static void addTaskTo5SecTaskQueue(RunnableFunction function) {
        fiveSecTaskQueue.add(function);
    }

    /**
     * Add a function to be executed in the 30 Sec queue
     * @param function Function to run
     */
    public static void addTaskTo30SecTaskQueue(RunnableFunction function) {
        fiveSecTaskQueue.add(function);
    }

    /**
     * Add a function to be executed in the 5 Min queue
     * @param function Function to run
     */
    public static void addTaskTo5MinTaskQueue(RunnableFunction function) {
        fiveSecTaskQueue.add(function);
    }

    public static void addTaskTo5SecTaskQueue() {

        // @TODO: for testing, to be removed later
        addTaskTo5SecTaskQueue(() -> {
            LogUtil.logWarning(MyzticCore.getPrefix(), "Printing from timed task 1 ");
        });
        addTaskTo5SecTaskQueue(() -> {
            LogUtil.logWarning(MyzticCore.getPrefix(), "Printing from timed task 2 ");
        });
        addTaskTo5SecTaskQueue(() -> {
            LogUtil.logWarning(MyzticCore.getPrefix(), "Printing from timed task 3 ");
        });
        addTaskTo5SecTaskQueue(() -> {
            LogUtil.logWarning(MyzticCore.getPrefix(), "Printing from timed task 4 ");
        });
        addTaskTo5SecTaskQueue(() -> {
            LogUtil.logWarning(MyzticCore.getPrefix(), "Printing from timed task 5 ");
        });
//        addTaskTo5SecTaskQueue(arg ->5{
//            arg = "Hey";
//            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 2 " + arg);
//        });
//        addTaskTo5SecTaskQueue(arg -> {
//            arg = "Hey";
//            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 3 " + arg);
//        });
//        addTaskTo5SecTaskQueue(arg -> {
//            arg = "Hey";
//            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 4 " + arg);
//        });
//        addTaskTo5SecTaskQueue(arg -> {
//            arg = "Hey";
//            LogUtil.logError(MyzticCore.getPrefix(), "Printing from timed task 5 " + arg);
//        });
    }
}
