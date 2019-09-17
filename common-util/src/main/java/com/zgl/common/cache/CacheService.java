package com.zgl.common.cache;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zgl
 * @date 2019/9/11 下午5:27
 */
public interface CacheService {

	/**
	 * 根据key查询缓存中存在的数据,返回key-value形式的map,供业务层调用
	 * @param keys 缓存key, 支持批量
	 * @param classType 返回值类型
	 * @param mapper 自定义返回值map的key
	 * @param <T>
	 * @return
	 */
	<T> Map<String, T> cacheQueryExistInfoMap(List<String> keys, Class<T> classType, Function<T, String> mapper);
}