package com.zgl.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于fastjson的工具类
 * @author zgl
 * @date 2019/3/27 上午11:14
 */
public class JsonUtil {
	/**
	 * 默认序列化配置
	 */
	private static SerializerFeature[] defaultFeatures;

	/**
	 * 序列化时写入类型名称序列化配置
	 */
	private static SerializerFeature[] writeClassNameFeatures;

	static {
		List<SerializerFeature> features = new ArrayList<>();
		features.add(SerializerFeature.WriteDateUseDateFormat);
		defaultFeatures = features.toArray(new SerializerFeature[0]);
		features.add(SerializerFeature.WriteClassName);
		writeClassNameFeatures = features.toArray(new SerializerFeature[0]);
	}

	/**
	 * json转换成map
	 * @param jsonString
	 * @return
	 */
	public static Map json2Map(String jsonString){
		return JSON.parseObject(jsonString, Map.class);
	}

	/**
	 * json字符串转换成对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Bean(String jsonString, Class<T> clazz){
		T t = null;
		try {
			t = JSON.parseObject(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 对象转换成json字符串
	 * @param bean
	 * @return
	 */
	public static String bean2Json(Object bean){
		return JSON.toJSONString(bean, defaultFeatures);
	}

	/**
	 * 对象转换成json字符串，并写入类型名称
	 * @param bean
	 * @return
	 */
	public static String bean2JsonWriteType(Object bean) {
		return JSON.toJSONString(bean, writeClassNameFeatures);
	}

	/**
	 * json字符串转换成List集合
	 * (需要实体类)
	 * @param jsonString
	 * @return
	 */
	public static <T> List<T> json2List(String jsonString, Class<T> clazz){
		List<T> list = null;
		try {
			list = JSON.parseArray(jsonString, clazz);
		} catch (Exception e) {
		}
		return list;
	}

	/**
	 * List集合转换成json字符串
	 * @param list
	 * @return
	 */
	public static String list2Json(List list){
		return JSONArray.toJSONString(list);
	}
}