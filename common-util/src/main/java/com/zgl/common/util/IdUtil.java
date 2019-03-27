package com.zgl.common.util;

import java.util.UUID;

/**
 * 唯一id工具类
 * @author zgl
 * @date 2019/3/27 上午11:37
 */
public class IdUtil {
	/**
	 * 获取uuid
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}