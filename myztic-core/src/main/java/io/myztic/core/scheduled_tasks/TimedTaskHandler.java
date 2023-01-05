package io.myztic.core.scheduled_tasks;

import io.myztic.core.exceptions.UtilityClassException;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class TimedTaskHandler {

    private static final BlockingQueue<Function> fiveSecTaskQueue = new LinkedBlockingQueue<>(10);

    private TimedTaskHandler() {
        throw new UtilityClassException();
    }

    public static void runTaskEvery5Seconds() {
//        try {
//            Function<T,V> func = fiveSecTaskQueue.take();
//            func.apply(null);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void runTaskEvery5Seconds(Function<Boolean, Boolean> func, Boolean param) {
        func.apply(param);
        fiveSecTaskQueue.add(func);
    }

    public static void runTaskEvery30Seconds() {

    }

    public static void runTaskEvery5Minutes() {

    }
}
