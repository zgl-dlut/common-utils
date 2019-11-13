package com.zgl.common.util;

import com.alibaba.fastjson.JSONObject;
import com.zgl.common.enums.ErrorCodeEnum;
import com.zgl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019-05-14 14:32
 */
@Slf4j
public class HttpUtils {
	public static String getUrlParam(String url, Map<String, Object> paramMap) {
		List<NameValuePair> paramList = new ArrayList<>();

		//拼接列表
		StringBuilder listParam = new StringBuilder();
		changListToUrl(paramMap, paramList, listParam);

		return url + "?" + URLEncodedUtils.format(paramList, Consts.UTF_8) + listParam.toString();
	}

	/**
	 * 带头部的get
	 * @param paramMap 参数
	 * @param url
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> headMap, Map<String, Object> paramMap) {
		log(headMap, paramMap, url);
		if (paramMap == null || url == null ) {
			throw new BusinessException();
		}
		try {
			url = getUrlParam(url, paramMap);
			Request request = Request.Get(url)
					.connectTimeout(5000)
					.socketTimeout(5000);
			if (headMap != null) {
				for (String key : headMap.keySet()) {
					request.setHeader(key, String.valueOf(headMap.get(key)));
				}
			}

			String s = request.execute().returnContent().asString(Consts.UTF_8);
			log.info("返回结果->>" + s);
			return s;
		} catch (Exception e) {
			log.error("远程调用接口出错:"+url+",状态码："+ e.getMessage());
			throw new BusinessException();
		}
	}

	/**
	 * 请求体为对象
	 * @param headerMap
	 * @param json
	 * @param url
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> headerMap, String json) {
		log(headerMap, json, url);
		if (json == null || url == null ) {
			throw new BusinessException();
		}
		try {
			Request request = Request.Post(url).connectTimeout(5000).socketTimeout(5000);
			if (headerMap != null) {
				for (String key : headerMap.keySet()) {
					request.setHeader(key, String.valueOf(headerMap.get(key)));
				}
			}
			String s =request.bodyString(json, ContentType.APPLICATION_JSON).execute().returnContent().asString(Consts.UTF_8);
			log.info("返回结果->>" + s);
			return s;
		} catch (Exception e) {
			log.error("远程调用接口出错:"+url+",状态码："+ e.getMessage());
			throw new BusinessException();
		}
	}

	/**
	 * 请求体为对象
	 * @param headerMap
	 * @param json
	 * @param url
	 * @return
	 */
	public static String doPut(String url, Map<String, Object> headerMap, String json) {
		log(headerMap, json, url);
		if (json == null || url == null ) {
			throw new BusinessException();
		}
		try {
			Request request = Request.Put(url).connectTimeout(5000).socketTimeout(5000);
			if (headerMap != null) {
				for (String key : headerMap.keySet()) {
					request.setHeader(key, String.valueOf(headerMap.get(key)));
				}
			}
			String s =request.bodyString(json, ContentType.APPLICATION_JSON).execute().returnContent().asString(Consts.UTF_8);
			log.info("返回结果->>" + s);
			return s;
		} catch (Exception e) {
			log.error("远程调用接口出错！{}", e.getMessage(), e);
			throw new RuntimeException("远程调用接口出错！" + e.getMessage());
		}
	}


	/**
	 * 不带头部的post
	 * @param paramMap 参数
	 * @param url
	 * @return
	 */
	public static String doPostByBodyForm(String url, Map<String, Object> headerMap, Map<String, Object> paramMap) {
		log(headerMap,paramMap, url);
		if (paramMap == null || url == null ) {
			throw new BusinessException();
		}
		try {
			Request request = Request.Post(url)
					.connectTimeout(5000)
					.socketTimeout(5000);
			if (null != headerMap) {
				for (String key : headerMap.keySet()) {
					request.setHeader(key, String.valueOf(headerMap.get(key)));
				}
			}
			List<NameValuePair> paramSpairs = new ArrayList<>();
			for (String key : paramMap.keySet()) {
				paramSpairs.add(new BasicNameValuePair(key, String.valueOf(paramMap.get(key))));
			}

			String s = request.bodyForm(paramSpairs, Charset.forName("utf-8")).execute().returnContent().asString(Consts.UTF_8);
			log.info("返回结果->>" + s);
			return s;
		} catch (Exception e) {
			log.error("远程调用接口出错:"+url+",状态码："+ e.getMessage());
			throw new BusinessException();
		}
	}
	public static InputStream doPostFromStream(String url, Map<String, Object> headerMap, Map<String, Object> paramMap) {
		log(headerMap, paramMap, url);
		if (paramMap == null || url == null) {
			throw new BusinessException();
		}
		try {
			Request request = Request.Post(url)
					.connectTimeout(5000)
					.socketTimeout(5000);
			if (null != headerMap) {
				for (String key : headerMap.keySet()) {
					request.setHeader(key, String.valueOf(headerMap.get(key)));
				}
			}
			List<NameValuePair> paramSpairs = new ArrayList<>();
			for (String key : paramMap.keySet()) {
				paramSpairs.add(new BasicNameValuePair(key, String.valueOf(paramMap.get(key))));
			}
			return request.bodyForm(paramSpairs, Charset.forName("utf-8")).execute().returnContent().asStream();
		} catch (IOException e) {
			log.error("远程调用接口出错:" + url + ",状态码：" + e.getMessage());
			throw new BusinessException();
		}
	}

	public static InputStream doGetStream(String url, Map<String, Object> headMap, Map<String, Object> paramMap) {
		log(headMap, paramMap, url);
		if (paramMap == null || url == null ) {
			throw new BusinessException();
		}
		try {
			url = getUrlParam(url, paramMap);
			Request request = Request.Get(url)
					.connectTimeout(5000)
					.socketTimeout(5000);
			if (headMap != null) {
				for (String key : headMap.keySet()) {
					request.setHeader(key, String.valueOf(headMap.get(key)));
				}
			}
			return request.execute().returnContent().asStream();
		} catch (Exception e) {
			log.error("远程调用接口出错:"+url+",状态码："+ e.getMessage());
			throw new BusinessException();
		}
	}


	private static void changListToUrl(Map<String, Object> paramMap, List<NameValuePair> paramList, StringBuilder listParam) {
		for (Object key : paramMap.keySet()) {
			if (paramMap.get(key) instanceof List) {
				List list = (List) paramMap.get(key);
				for (Object object : list) {
					listParam.append("&").append(String.valueOf(key)).append("=").append(String.valueOf(object));
				}
			} else {
				paramList.add(new BasicNameValuePair(String.valueOf(key), String.valueOf(paramMap.get(key))));
			}
		}
	}

	private static void log(Map<String, Object> headerMap, Map<String, Object> paramMap, String url) {
		log.info("请求路径->>" + url);
		log.info("请求头部->>" + headerMap);
		log.info("请求参数->>" + JSONObject.toJSON(paramMap));
	}

	private static void log(Map<String, Object> headerMap, Object json, String url) {
		log.info("请求路径->>" + url);
		log.info("请求头部->>" + headerMap);
		log.info("请求参数->>" + JSONObject.toJSON(json));
	}

	private static void log(String url, String json, Map<String, String> headerMap) {
		log.info("请求路径->>" + url);
		log.info("请求头部->>" + headerMap);
		log.info("请求参数->>" + json);
	}

	private static void log(String url, String json) {
		log(url, json, new HashMap<>());

	}

}
