package io.myztic.core.scheduled_tasks;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.logging.LogUtil;

public final class TaskTimer5Sec implements Runnable {
    @Override
    public void run() {
        LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 5 Sec interval task...", ConfigProvider.getInst().DEBUG_MODE);
        TimedTaskHandler.runTaskEvery5Seconds();
    }
}
