package com.ces.portal.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecuteTaskUtil {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 200, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public static void excuteTask(Runnable command) {
        threadPoolExecutor.execute(command);
    }
}
