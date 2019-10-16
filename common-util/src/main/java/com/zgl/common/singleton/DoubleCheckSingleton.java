package com.zgl.common.singleton;

/**
 * @author zgl
 * @date 2019/8/20 下午10:51
 */
public class DoubleCheckSingleton {

	//如果设置成volatile可以防止指令重排序
	private volatile static DoubleCheckSingleton instance;

	private DoubleCheckSingleton(){}

	/**
	 * 双重校验线程低概率不安全(不加volatile的情况下)
	 */
	public static DoubleCheckSingleton getInstance() {
		if (null == instance) {
			synchronized (DoubleCheckSingleton.class) {
				if (null == instance) {
					//不是原子操作
					instance = new DoubleCheckSingleton();
				}
			}
		}
		return instance;
	}
}