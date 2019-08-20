package com.zgl.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zgl
 * @date 2019/8/20 下午5:09
 */
@Component
@Slf4j
public class RedisUtils {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	private static RedisUtils redisUtils;

	private static final int CACHE_TIME = 60;

	@PostConstruct
	public void init() {
		redisUtils = this;
		redisUtils.redisTemplate = this.redisTemplate;
		redisUtils.stringRedisTemplate = this.stringRedisTemplate;
	}


	/**
	 * MSET
	 *
	 * @param multiMap
	 */
	@SuppressWarnings("unchecked")
	public static void cachePut(Map<String, String> multiMap) throws DataAccessException {
		redisUtils.stringRedisTemplate.executePipelined(((RedisCallback) connection -> {
			StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
			for (String key : multiMap.keySet()) {
				stringRedisConn.expire(key, CACHE_TIME);
			}
			stringRedisConn.mSetString(multiMap);
			return null;
		}));
		cachePutLog(multiMap);
	}

	/**
	 * MGET
	 *
	 * @param keys
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> cacheQuery(List<String> keys) {
		List<T> records = redisUtils.redisTemplate.opsForValue().multiGet(keys);
		if (Objects.nonNull(records)) {
			records = records.stream().filter(Objects::nonNull).collect(Collectors.toList());
		}
		cacheQueryLog(records);
		return records;
	}

	/**
	 * 查询缓存中存在的id列表
	 *
	 * @param ids
	 * @return
	 */
	public static List<String> getCacheExist(List<String> ids) {
		return ids.stream().filter(id -> Objects.nonNull(redisUtils.redisTemplate.opsForValue().get(id))).collect(Collectors.toList());
	}

	/**
	 * 查询缓存中不存在的id列表
	 *
	 * @param ids
	 * @return
	 */
	public static List<String> getCacheNotExist(List<String> ids) {
		return ids.stream().filter(id -> Objects.isNull(redisUtils.redisTemplate.opsForValue().get(id))).collect(Collectors.toList());
	}


	private static void cachePutLog(Object list) {
		log.info("缓存放入成功,信息为:{}", list);
	}

	private static void cacheQueryLog(Object list) {
		log.info("缓存查询成功,信息为:{}", list);
	}
}