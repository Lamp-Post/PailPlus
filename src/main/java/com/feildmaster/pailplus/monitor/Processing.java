package com.feildmaster.pailplus.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import org.spongepowered.api.service.scheduler.SynchronousScheduler;

public class Processing {

    private static final ThreadMXBean thread = ManagementFactory.getThreadMXBean();
    private static final Runtime runtime = Runtime.getRuntime();
    private static final SynchronousScheduler schedule = Util.getGame().getSyncScheduler();

    public static long memoryUsed() {
        return runtime.totalMemory() - runtime.freeMemory();
    }
    public static long memoryTotal() {
        return runtime.totalMemory();
    }
    public static long memoryMax() {
        return runtime.maxMemory();
    }

    public static int threadsUsed() {
        return thread.getThreadCount();
    }

    public static int tasks() {
        return schedule.getScheduledTasks().size();
    }
}
