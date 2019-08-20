package com.zgl.common.util;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author zgl
 * @date 2019/3/27 上午11:47
 */
public class HttpHelper {
	/**
	 * 获取请求Body
	 *
	 * @param request
	 * @return
	 */
	public static String getBodyString(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		List<String> list = new Vector<>();
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new Stack<>();
		List<String> list3 = new LinkedList<>();
		List<String> list4 = new AbstractSequentialList<String>() {
			@Override
			public ListIterator<String> listIterator(int index) {
				return null;
			}

			@Override
			public int size() {
				return 0;
			}
		};
		List<String> list5 = new AbstractList<String>() {
			@Override
			public String get(int index) {
				return null;
			}

			@Override
			public int size() {
				return 0;
			}
		};
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
	}
}