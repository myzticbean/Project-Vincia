package io.myztic.core.scheduled_tasks;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.logging.LogUtil;

public final class TaskTimer5Min implements Runnable {

    // implement a queue system,
    // whenever below method triggers it picks up a method from the queue and starts executing

    @Override
    public void run() {
        LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 5 Min interval task...", ConfigProvider.getInst().DEBUG_MODE);
        TimedTaskHandler.runTaskEvery5Minutes();
    }
}
