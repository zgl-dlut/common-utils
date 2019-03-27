package com.zgl.common.util;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Joiner;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 数据签名工具类
 * @author zgl
 * @date 2019/3/27 上午11:16
 */
public class SignUtil {
	private static final  String LINKER = "&";

	private static final String KEY_VALUE_SEPARATOR = "=";

	/**
	 * 需要传递TreeMap对象,默认将params已经进行排序
	 * @param params
	 * @param key
	 * @return
	 */
	public static String getSignature(Map<String, Object> params, String key){
		SortedMap<String, String> sortedParams = new TreeMap<>();
		params.forEach((k, v) -> {
			sortedParams.put(k, TypeUtils.castToString(v));
		});
		String signature = Joiner.on(LINKER).join(Joiner.on(LINKER).
						withKeyValueSeparator(KEY_VALUE_SEPARATOR).useForNull("").join(sortedParams),
				key);
		return DigestUtils.md5Hex(signature);
	}
}