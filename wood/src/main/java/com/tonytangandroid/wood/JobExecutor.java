package com.tonytangandroid.wood;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class JobExecutor implements Executor {

  static final int INITIAL_POOL_SIZE = 2;
  static final int MAX_POOL_SIZE = 5;
  static final int KEEP_ALIVE_TIME = 3;
  static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

  ThreadPoolExecutor threadPoolExecutor;

  public JobExecutor() {
    BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    this.threadPoolExecutor =
        new ThreadPoolExecutor(
            INITIAL_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            KEEP_ALIVE_TIME_UNIT,
            workQueue,
            new JobThreadFactory());
  }

  @Override
  public void execute(Runnable runnable) {
    this.threadPoolExecutor.execute(runnable);
  }

  static class JobThreadFactory implements ThreadFactory {
    static final String THREAD_NAME = "log_";
    private int counter = 0;

    @Override
    public Thread newThread(Runnable runnable) {
      return new Thread(runnable, THREAD_NAME + counter++);
    }
  }
}
