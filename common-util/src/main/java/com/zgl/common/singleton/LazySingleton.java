package com.zgl.common.singleton;

/**
 * @author zgl
 * @date 2019/8/20 下午6:12
 */
public class LazySingleton {

	private static LazySingleton instance;

	private LazySingleton() {

	}

	/**
	 * 懒汉非线程安全
	 */
	public static LazySingleton getUnsafeInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}

	/**
	 * 懒汉线程安全
	 */
	public static synchronized  LazySingleton getSageInstance() {
		if (instance == null) {
			instance = new LazySingleton();
		}
		return instance;
	}
}