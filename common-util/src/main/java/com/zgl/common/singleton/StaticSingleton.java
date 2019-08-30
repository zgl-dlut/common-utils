package com.zgl.common.singleton;

/**
 * @author zgl
 * @date 2019/8/20 下午10:36
 */
public class StaticSingleton {

	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}

	private StaticSingleton() {
		System.out.println("静态内部类单例模式加载~~~");
	}

	//线程安全
	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}

	public static void main(String[] args) {
		StaticSingleton instance1 = StaticSingleton.getInstance();
		StaticSingleton instance2 = StaticSingleton.getInstance();
	}
}