package com.zgl.common.util;

import java.util.*;

/**
 * @author zgl
 * @date 2019/9/11 下午4:31
 */
public class ListUtils {
	/**
	 * 删除list重复元素
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> removeDuplicate(List<T> params) {
		Set h = new HashSet(Arrays.asList(params.toArray()));
		return new ArrayList<>(h);
	}

	@SuppressWarnings("unchecked")
	public static List<String> getDifferent(List<String> collmax, List<String> collmin) {
		//使用LinkedList防止差异过大时,元素拷贝
		List<String> csReturn = new LinkedList();
		List<String> max = collmax;
		List<String> min = collmin;
		//先比较大小,这样会减少后续map的if判断次数
		if (collmax.size() < collmin.size()) {
			max = collmin;
			min = collmax;
		}
		//直接指定大小,防止再散列
		Map<String, Integer> map = new HashMap<>(max.size());
		for (String object : max) {
			map.put(object, 1);
		}
		for (String object : min) {
			if (map.get(object) == null) {
				csReturn.add(object);
			} else {
				map.put(object, 2);
			}
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				csReturn.add(entry.getKey());
			}
		}
		return csReturn;
	}
}