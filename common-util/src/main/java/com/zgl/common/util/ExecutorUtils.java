package com.zgl.common.util;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.lang.Nullable;

import java.util.concurrent.*;

/**
 * @author zgl
 * @date 2019/8/20 下午5:39
 */
public class ExecutorUtils {
	private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("zgl-pool-%d").build();

	public static final ExecutorService EXECUTOR = getTtlThreadPool(16, 16, 60L);


	/**
	 *线程池包装
	 * @param corePoolSize
	 * @param maxPoolSize
	 * @param keepAliveTime
	 * @return
	 */
	public static ExecutorService getTtlThreadPool(int corePoolSize, int maxPoolSize, long keepAliveTime) {
		ExecutorService fixThreadPool = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(200), THREAD_FACTORY);
		return TtlExecutors.getTtlExecutorService(fixThreadPool);
	}

	/**
	 * 线程池包装
	 * @param executorService
	 * @return
	 */
	public static ExecutorService getTtlThreadPool(@Nullable ExecutorService executorService) {
		return TtlExecutors.getTtlExecutorService(executorService);
	}
}