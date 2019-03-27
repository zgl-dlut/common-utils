package com.zgl.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zgl
 * @date 2019/3/27 上午10:58
 */
public class BeanMapUtil {
	/**
	 * 对象转为Map
	 * @param source
	 * @param ignoreProperties
	 * @return
	 */
	public static Map beanToMap(Object source, String... ignoreProperties) {
		Class clazz = source.getClass();
		Map resultMap = new HashMap();
		Field[] allFields = clazz.getDeclaredFields();
		List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
		for (Field field : allFields) {
			String propertyName = field.getName();
			if (ignoreList == null || !ignoreList.contains(propertyName)) {
				try {
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if (value != null && StringUtils.isNotEmpty(value.toString())) {
							resultMap.put(propertyName, value);
						}
					}
				} catch (Exception e) {
					throw new FatalBeanException("Could not convert to map", e);
				}
			}
		}
		return resultMap;
	}
}