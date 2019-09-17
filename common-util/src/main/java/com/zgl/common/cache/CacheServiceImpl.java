package com.zgl.common.cache;

import com.google.common.collect.Maps;
import com.zgl.common.util.ListUtils;
import com.zgl.common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author zgl
 * @date 2019/9/11 下午5:33
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService{

	@Override
	public <T> Map<String, T> cacheQueryExistInfoMap(List<String> keys, Class<T> classType, Function<T, String> mapper) {
		keys = ListUtils.removeDuplicate(keys);
		List<T> infoList = RedisUtils.cacheQuery(keys, classType);
		Map<String, T> resultMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(infoList)) {
			for (T t : infoList) {
				resultMap.put(mapper.apply(t), t);
			}
		}
		log.info("缓存查询成功:{}", resultMap);
		return resultMap;
	}
}