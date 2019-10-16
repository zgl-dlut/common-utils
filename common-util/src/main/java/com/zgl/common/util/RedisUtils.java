package com.zgl.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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
	 * 放入单个缓存
	 * @param key
	 * @param val
	 * @param <T>
	 */
	public static <T> void cachePut(String key, T val) {
		redisUtils.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(val));
	}

	/**
	 * 放入单个缓存-指定缓存时间
	 * @param key
	 * @param val
	 * @param timeout
	 * @param unit
	 * @param <T>
	 */
	public static <T> void cachePut(String key, T val, long timeout, TimeUnit unit) {
		redisUtils.stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(val), timeout, unit);
	}

	/**
	 * 利用管道批量存数据(解决mset无法设置过期时间)
	 * @param records 插入list
	 * @param classType 缓存对象类型
	 * @param mapper 缓存key策略: 类的包名+字段拼接
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public static <T, K> void batchCachePut(List<T> records, Class<T> classType, Function<T, K> mapper) {
		if (CollectionUtils.isEmpty(records)) {
			return;
		}
		Map<String, T> multiMap = Maps.newHashMap();
		records.forEach((t) -> multiMap.put(classType.getName() + mapper.apply(t).toString(), t));
		redisUtils.stringRedisTemplate.executePipelined(new SessionCallback<T>() {
			@Override
			public Boolean execute(RedisOperations operations) throws DataAccessException  {
				for (Map.Entry<String, T> entry : multiMap.entrySet()) {
					operations.opsForValue().set(entry.getKey(), JSON.toJSONString(entry.getValue()), CACHE_TIME, TimeUnit.SECONDS);
				}
				return null;
			}
		});
	}

	/**
	 * 利用管道批量存数据-指定时间
	 */
	@SuppressWarnings("unchecked")
	public static <T, K> void batchCachePut(List<T> records, Class<T> classType, Function<T, K> mapper, long timeout, TimeUnit unit) {
		if (CollectionUtils.isEmpty(records)) {
			return;
		}
		Map<String, T> multiMap = Maps.newHashMap();
		records.forEach((t) -> multiMap.put(classType.getName() + mapper.apply(t).toString(), t));
		redisUtils.stringRedisTemplate.executePipelined(new SessionCallback<T>() {
			@Override
			public Boolean execute(RedisOperations operations) throws DataAccessException  {
				for (Map.Entry<String, T> entry : multiMap.entrySet()) {
					operations.opsForValue().set(entry.getKey(), JSON.toJSONString(entry.getValue()), timeout, unit);
				}
				return null;
			}
		});
	}

	/**
	 * 利用管道批量存数据
	 * @param multiMap 缓存map(业务层已经封装好)
	 * @param classType 缓存对象类型
	 * @param <T>
	 */
	@SuppressWarnings("unchecked")
	public static <T> void batchCachePut(Map<String, T> multiMap, Class<T> classType) {
		if (Objects.isNull(multiMap)) {
			return;
		}
		redisUtils.stringRedisTemplate.executePipelined(new SessionCallback<String>() {
			@Override
			public String execute(RedisOperations operations) throws DataAccessException  {
				for (Map.Entry<String, T> entry : multiMap.entrySet()) {
					operations.opsForValue().set(classType.getName() + entry.getKey(), JSON.toJSONString(entry.getValue()), CACHE_TIME, TimeUnit.SECONDS);
				}
				return null;
			}
		});
	}

	/**
	 * 利用管道批量存数据-指定时间
	 */
	@SuppressWarnings("unchecked")
	public static <T> void batchCachePut(Map<String, T> multiMap, Class<T> classType, long timeout, TimeUnit unit) {
		if (Objects.isNull(multiMap)) {
			return;
		}
		redisUtils.stringRedisTemplate.executePipelined(new SessionCallback<String>() {
			@Override
			public String execute(RedisOperations operations) throws DataAccessException  {
				for (Map.Entry<String, T> entry : multiMap.entrySet()) {
					operations.opsForValue().set(classType.getName() + entry.getKey(), JSON.toJSONString(entry.getValue()), timeout, unit);
				}
				return null;
			}
		});
	}

	/**
	 * 查询单个key
	 * @param key
	 * @param classType
	 * @param <T>
	 * @return
	 */
	public static <T> T cacheQuery(String key, Class<T> classType) {
		String val = redisUtils.stringRedisTemplate.opsForValue().get(key);
		if (StringUtils.isBlank(val)) {
			return null;
		}
		return JsonUtil.json2Bean(val, classType);
	}

	/**
	 * 查询单个key,返回jsonString
	 * @param key
	 * @return
	 */
	public static String cacheQuery(String key) {
		return redisUtils.stringRedisTemplate.opsForValue().get(key);
	}
	/**
	 * MGET 返回list
	 * @param keys 缓存key
	 * @param classType 缓存对象类型
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> batchCacheQuery(List<String> keys, Class<T> classType) {
		keys = ListUtils.removeDuplicate(keys);
		if(CollectionUtils.isEmpty(keys)) {
			return new ArrayList<>();
		}
		List<String> cacheKeys = new ArrayList<>();
		keys.forEach((key) -> cacheKeys.add(classType.getName() + key));
		List<String> jsonRecords = redisUtils.stringRedisTemplate.opsForValue().multiGet(cacheKeys);
		if (Objects.nonNull(jsonRecords)) {
			jsonRecords = jsonRecords.stream().filter(Objects::nonNull).collect(Collectors.toList());
		}
		List<T> records = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(jsonRecords)) {
			for (String json : jsonRecords) {
				records.add(JsonUtil.json2Bean(json, classType));
			}
		}
		return records;
	}


	/**
	 * MGET 返回map
	 * @param keys 缓存key
	 * @param classType 缓存对象类型
	 * @param mapper 缓存key, 与放入缓存的字段一致
	 * @param <T>
	 * @return
	 */
	public static <T, K> Map<String, T> batchCacheQueryMap(List<String> keys, Class<T> classType, Function<T, K> mapper) {
		keys = ListUtils.removeDuplicate(keys);
		List<T> infoList = batchCacheQuery(keys, classType);
		Map<String, T> resultMap = Maps.newHashMap();
		if (CollectionUtils.isNotEmpty(infoList)) {
			for (T t : infoList) {
				resultMap.put(mapper.apply(t).toString(), t);
			}
		}
		return resultMap;
	}

	/**
	 * 查询缓存中存在的id列表
	 *
	 * @param keys
	 * @return
	 */
	public static List<String> getCacheExistIds(List<String> keys) {
		return keys.stream().filter(key -> Objects.nonNull(redisUtils.redisTemplate.opsForValue().get(key))).collect(Collectors.toList());
	}

	/**
	 * 查询缓存中不存在的id列表
	 *
	 * @param keys
	 * @return
	 */
	public static List<String> getCacheNotExistIds(List<String> keys) {
		return keys.stream().filter(key -> Objects.isNull(redisUtils.redisTemplate.opsForValue().get(key))).collect(Collectors.toList());
	}

	private static void cachePutLog(Object list) {
		log.info("缓存放入成功,信息为:{}", list);
	}

	private static void cacheQueryLog(Object list) {
		log.info("缓存查询成功,信息为:{}", list);
	}
}