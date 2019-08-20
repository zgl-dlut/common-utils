package com.zgl.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.zgl.common.enums.ErrorCodeEnum;
import com.zgl.common.enums.ServiceRespEnum;
import com.zgl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgl
 * @date 2019/8/20 下午5:25
 */
@Slf4j
public class ParseUtils {

	/**
	 * 检测是否抛异常
	 * @param response
	 * @param serviceRespEnum
	 */
	private static JSONObject parseResponse(String response, ServiceRespEnum serviceRespEnum) {
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(response);
		}
		catch (Exception e) {
			throw new BusinessException(ErrorCodeEnum.SYS_FAIL.getCode(), "json解析错误："+e.getMessage()+"-"+response);
		}
		String code = jsonObject.getString(serviceRespEnum.getCodeFieldName());
		if (!serviceRespEnum.getSuccessCode().equals(code)) {
			String message = jsonObject.getString(serviceRespEnum.getMsgFieldName());
			if ((Strings.isNullOrEmpty(serviceRespEnum.getSystemErrorCode().toString())
					|| serviceRespEnum.getSystemErrorCode().equals(code))
					&& (Strings.isNullOrEmpty(message)
					|| message.length()>300
					|| message.toLowerCase().contains("exception"))) {
				message = "系统出现异常，请联系系统管理员("+code+")";
			}
			throw new BusinessException(ErrorCodeEnum.SYS_HTTP_SYSTEM_ERROR.getCode(), message);
		}
		return jsonObject;
	}

	/**
	 * 解析结果为对象
	 * @param response
	 * @param serviceRespEnum
	 */
	public static void parse(String response, ServiceRespEnum serviceRespEnum) {
		parseResponse(response, serviceRespEnum);
	}

	/**
	 * 解析结果为对象
	 * @param response
	 * @param targetType
	 * @param serviceRespEnum
	 * @param <T>
	 */
	public static <T> T parse(String response, Class<T> targetType, ServiceRespEnum serviceRespEnum) {
		JSONObject jsonObject = parseResponse(response, serviceRespEnum);
		return jsonObject.getObject(serviceRespEnum.getDataFieldName(), targetType);
	}

	/**
	 * 将结果解析为集合
	 * @param response
	 * @param targetType
	 * @param serviceRespEnum
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> parseCollection(String response, Class<T> targetType, ServiceRespEnum serviceRespEnum) {
		List<T> result = new ArrayList<>();
		JSONObject jsonObject = parseResponse(response, serviceRespEnum);
		JSONArray data = jsonObject.getJSONArray(serviceRespEnum.getDataFieldName());
		if(data != null){
			result = data.toJavaList(targetType);
		}
		return result;
	}
}