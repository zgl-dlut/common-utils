package com.zgl.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zgl.common.enums.ServiceRespEnum;
import com.zgl.common.util.HttpUtils;
import com.zgl.common.util.ParseUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/8/20 下午5:52
 */
@Slf4j
public class BaseClient {
	protected String base;
	protected ServiceRespEnum serviceRespEnum;

	/************* GET ********************/
	/**
	 *
	 * @param path
	 * @param paramMap
	 * @param headMap
	 */
	protected <T> T doGet(String path, Map<String, Object> headMap, Map<String, Object> paramMap) {
		return doGet(path, headMap, paramMap, null);
	}

	/**
	 * get 获取单个对象
	 */
	protected <T> T doGet(String path, Map<String, Object> headerMap, Map<String, Object> paramMap, Class<T> targetType) {
		T result = null;
		String response =  httpGET(path, headerMap, paramMap);
		if (targetType != null) {
			result = ParseUtils.parse(response, targetType, serviceRespEnum);
		}else{
			ParseUtils.parse(response, serviceRespEnum);
			return (T) response;
		}
		return result;
	}


	/**
	 * get 获取多个对象
	 */
	protected <T> List<T> doGetCollection(String path, Map<String, Object> headerMap, Map<String, Object> paramMap, Class<T> targetType) {
		List<T> result = null;
		String response =  httpGET(path, headerMap, paramMap);
		if (targetType != null) {
			result = ParseUtils.parseCollection(response, targetType, serviceRespEnum);
		}
		return result;
	}

	protected void dealHeaderMap(Map<String, Object> headerMap) {

	}

	protected void dealHeaderMapGet(Map<String, Object> headerMap, Map<String, Object> paramMap) {
		dealHeaderMap(headerMap);
	}

	protected void dealHeaderMapPost(Map<String, Object> headerMap, Map<String, Object> paramMap) {
		dealHeaderMap(headerMap);
	}

	protected void dealHeaderMapPost(Map<String, Object> headerMap, Object body) {
		dealHeaderMap(headerMap);
	}

	/************* GET *******************/
	protected String httpGET(String path, Map<String, Object> headerMap, Map<String, Object> paramMap) {
		if (headerMap == null) {
			headerMap = new HashMap<>();
		}
		dealHeaderMapGet(headerMap, paramMap);
		return HttpUtils.doGet(base + path, headerMap, paramMap);
	}


	/************* POST *******************/
	protected String httpPost(String path, Map<String, Object> headerMap, Object body) {
		if (headerMap == null) {
			headerMap = new HashMap<>();
		}
		dealHeaderMapPost(headerMap, body);
		return HttpUtils.doPost(base + path, headerMap, JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
	}

	/************* put *******************/
	protected String httpPut(String path, Map<String, Object> headerMap, Object body) {
		if (headerMap == null) {
			headerMap = new HashMap<>();
		}
		dealHeaderMapPost(headerMap, body);
		return HttpUtils.doPut(base + path, headerMap, JSON.toJSONString(body, SerializerFeature.DisableCircularReferenceDetect));
	}


	/************* POST *******************/
	protected String httpPost(String path, Map<String, Object> headerMap, Map<String, Object> paramMap) {
		if (headerMap == null) {
			headerMap = new HashMap<>();
		}
		if (paramMap == null) {
			paramMap = new HashMap<>();
		}
		dealHeaderMapPost(headerMap, paramMap);
		return HttpUtils.doPostByBodyForm(base + path, headerMap, paramMap);
	}

	/**
	 * 无对象返回
	 * @param path
	 * @param body
	 * @param headerMap
	 * @return
	 */
//	protected void doPost(String path, Map<String, Object> headerMap, Object body) {
//		doPost(path, headerMap, body, null);
//	}

	/**
	 * 获取单个对象
	 * @param path
	 * @param body
	 * @return
	 */
	protected <T> T doPost(String path, Object body, Class<T> targetType) {
		return doPost(path, new HashMap<>(), body, targetType);
	}

	protected <T> T doPostWithForm(String path, Map<String, Object> headerMap, Map<String, Object> paramMap, Class<T> targetType) {
		T result = null;
		String response =  httpPost(path, headerMap, paramMap);
		if (targetType != null) {
			result = ParseUtils.parse(response, targetType, serviceRespEnum);
		}
		else{
			ParseUtils.parse(response, serviceRespEnum);
		}
		return result;
	}


	protected <T> List<T> doPostCollectionWithForm(String path, Map<String, Object> paramMap, Class<T> targetType) {
		return doPostCollectionWithForm(path, new HashMap<>(), paramMap, targetType);
	}

	protected <T> List<T> doPostCollectionWithForm(String path, Map<String, Object> headerMap, Map<String, Object> paramMap, Class<T> targetType) {
		List<T> result = new ArrayList<>();
		String response =  httpPost(path, headerMap, paramMap);
		if (targetType != null) {
			result = ParseUtils.parseCollection(response, targetType, serviceRespEnum);
		}
		return result;
	}


	/**
	 * 获取单个对象
	 * @param path
	 * @param body
	 * @param headerMap
	 * @return
	 */
	protected <T> T doPost(String path, Map<String, Object> headerMap, Object body, Class<T> targetType) {
		T result = null;
		String response =  httpPost(path, headerMap, body);
		if (targetType != null) {
			result = ParseUtils.parse(response, targetType, serviceRespEnum);
		}else{
			ParseUtils.parse(response, serviceRespEnum);
			result = (T) response;
		}
		return result;
	}

	/**
	 * put请求方式
	 * @param path
	 * @param headerMap
	 * @param body
	 * @param targetType
	 * @param <T>
	 * @return
	 */
	protected <T> T doPut(String path, Map<String, Object> headerMap, Object body, Class<T> targetType) {
		T result = null;
		String response =  httpPut(path, headerMap, body);
		if (targetType != null) {
			result = ParseUtils.parse(response, targetType, serviceRespEnum);
		}else{
			ParseUtils.parse(response, serviceRespEnum);
			result = (T) response;
		}
		return result;
	}

	/**
	 * 获取数组格式
	 * @param path
	 * @param body
	 * @return
	 */
	protected <T> List<T> doPostCollection(String path, Object body, Class<T> targetType) {
		return doPostCollection(path, new HashMap<>(), body,  targetType);
	}

	/**
	 * 获取数组格式
	 * @param path
	 * @param body
	 * @param headerMap
	 * @return
	 */
	protected <T> List<T> doPostCollection(String path, Map<String, Object> headerMap, Object body, Class<T> targetType) {
		List<T> result = new ArrayList<>();
		String response =  httpPost(path, headerMap, body);
		if (targetType != null) {
			result = ParseUtils.parseCollection(response, targetType, serviceRespEnum);
		}
		else {
			ParseUtils.parse(response, serviceRespEnum);
		}
		return result;
	}
}