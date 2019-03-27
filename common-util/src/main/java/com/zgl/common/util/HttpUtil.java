package com.zgl.common.util;

import com.zgl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/3/27 上午10:10
 */
@Component
@Slf4j
public class HttpUtil {

	@Autowired
	private RestTemplate restTemplate;

	private static HttpUtil httpUtil;

	@PostConstruct
	public void init() {
		httpUtil = this;
		httpUtil.restTemplate = this.restTemplate;
	}

	/**
	 * get请求
	 *
	 * @param url 请求url
	 * @return
	 */
	public static String doGet(String url) {
		return doGet(url, null, null);
	}

	/**
	 * get请求
	 *
	 * @param url    请求url
	 * @param params 请求参数
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> params) {
		return doGet(url, null, params);
	}

	/**
	 * get请求
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params) {
		return doGet(url, headers, params, null);
	}

	/**
	 * get请求
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @param uriVariables
	 * @return
	 */
	public static String doGet(String url, Map<String, Object> headers, Map<String, Object> params, Object... uriVariables) {
		HttpHeaders headerList = new HttpHeaders();
		if (headers != null) {
			headers.forEach((k, v) -> headerList.add(k, String.valueOf(v)));
		}
		HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headerList);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		if (params != null) {
			params.forEach((k, v) -> builder.queryParam(k, v));
		}
		return execute(builder.build(false).toString(), httpEntity, HttpMethod.GET, uriVariables);
	}

	/**
	 * post请求，发送json数据
	 *
	 * @param url
	 * @param body
	 * @return
	 */
	public static String doPost(String url, Object body) {
		return doPost(url, null, body);
	}

	/**
	 * post请求，发送json数据
	 *
	 * @param url
	 * @param headers
	 * @param body
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> headers, Object body) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			headers.forEach((k, v) -> httpHeaders.add(k, String.valueOf(v)));
		}
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		List<MediaType> acceptableMediaTypes = new ArrayList<>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		httpHeaders.setAccept(acceptableMediaTypes);
		HttpEntity<Object> httpEntity = new HttpEntity<>(body, httpHeaders);
		return execute(url, httpEntity, HttpMethod.POST);
	}

	/**
	 * post请求，发送表单数据
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPostForm(String url, Map<String, Object> params) {
		return doPostForm(url, null, params);
	}

	/**
	 * post请求，发送表单数据
	 *
	 * @param url
	 * @param headers
	 * @param params
	 * @return
	 */
	public static String doPostForm(String url, Map<String, Object> headers, Map<String, Object> params) {
		HttpHeaders httpHeaders = new HttpHeaders();
		if (headers != null) {
			headers.forEach((k, v) -> httpHeaders.add(k, String.valueOf(v)));
		}
		httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		if (params != null) {
			params.forEach((k, v) -> parameters.add(k, String.valueOf(v)));
		}
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parameters, httpHeaders);
		return execute(url, httpEntity, HttpMethod.POST);
	}

	/**
	 * 执行http请求
	 *
	 * @param url
	 * @param requestEntity
	 * @param httpMethod
	 * @return
	 */
	public static String execute(String url, HttpEntity<?> requestEntity, HttpMethod httpMethod, Object... uriVariables) {
		long startTime = System.currentTimeMillis();
		ResponseEntity<String> responseEntity;
		try {
			if (uriVariables != null && uriVariables.length > 0) {
				responseEntity = httpUtil.restTemplate.exchange(url, httpMethod, requestEntity, String.class, uriVariables);
			} else {
				responseEntity = httpUtil.restTemplate.exchange(url, httpMethod, requestEntity, String.class);
			}
		} catch (RestClientException e) {
			log.error("HTTP request error, url:{}, method:{}, headers:{}, params:{}", url, httpMethod, requestEntity.getHeaders(), requestEntity.getBody(), e);
			throw new BusinessException();
		}
		log.info("HTTP request success, url:{}, method:{}, headers:{}, params:{}, response status:{}, body:{} has finished in {}ms",
				url, httpMethod, requestEntity.getHeaders(), requestEntity.getBody(), responseEntity.getStatusCode(), responseEntity.getBody(), System.currentTimeMillis() - startTime);
		if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			return responseEntity.getBody();
		} else {
			log.error("HTTP request false, url:{}, method:{}, headers:{}, params:{}, response status:{}, body:{} has finished in {}ms");
			throw new BusinessException();
		}
	}

}