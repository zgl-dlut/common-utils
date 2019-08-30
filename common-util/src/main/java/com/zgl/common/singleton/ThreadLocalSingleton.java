package com.zgl.common.singleton;

/**
 * @author zgl
 * @date 2019/8/20 下午11:04
 */
public class ThreadLocalSingleton {

	/*private static ThreadLocal<ThreadLocalSingleton> singletonHolder = new ThreadLocal<ThreadLocalSingleton>() {

		@Override
		protected ThreadLocalSingleton initialValue() {
			return new ThreadLocalSingleton();
		}
	};
	private static ThreadLocal<ThreadLocalSingleton> singletonHolder = ThreadLocal.withInitial(() -> new ThreadLocalSingleton());*/

	private ThreadLocalSingleton(){}
	private static ThreadLocal<ThreadLocalSingleton> singletonHolder = ThreadLocal.withInitial(ThreadLocalSingleton::new);

	public static ThreadLocalSingleton getInstance() {
		return singletonHolder.get();
	}
}