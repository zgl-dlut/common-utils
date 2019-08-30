package com.zgl.common.singleton;

/**
 * @author zgl
 * @date 2019/8/20 下午10:33
 */
public class HungrySingleton {

	private static HungrySingleton instance = new HungrySingleton();
	private HungrySingleton(){}

	/**
	 * 饿汉模式线程安全
	 * @return
	 */
	public static HungrySingleton getInstance() {
		return instance;
	}
}