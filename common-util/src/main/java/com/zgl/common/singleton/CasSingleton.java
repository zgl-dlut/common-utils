package com.zgl.common.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zgl
 * @date 2019/8/20 下午11:12
 */
public class CasSingleton {

	private static AtomicReference<CasSingleton> instance = new AtomicReference<>();

	private CasSingleton(){}

	public static CasSingleton getInstance() {
		for(; ;) {
			CasSingleton current = instance.get();
			if (null != instance) {
				return current;
			}
			current = new CasSingleton();
			if (instance.compareAndSet(null, current)) {
				return current;
			}

		}
	}
}