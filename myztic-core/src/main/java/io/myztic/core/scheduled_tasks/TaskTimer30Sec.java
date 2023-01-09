package io.myztic.core.scheduled_tasks;

import io.myztic.core.MyzticCore;
import io.myztic.core.config.ConfigProvider;
import io.myztic.core.logging.LogUtil;

public final class TaskTimer30Sec implements Runnable {
    @Override
    public void run() {
        LogUtil.logDebugInfo(MyzticCore.getPrefix(), "Running 30 Sec interval task...", ConfigProvider.getInst().DEBUG_MODE);
        TimedTaskHandler.runTaskEvery30Seconds();
    }
}
