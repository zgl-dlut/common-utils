package com.zgl.common.util;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 可以修改header parameter body参数
 * @author zgl
 * @date 2019/3/27 上午11:44
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 用于保存读取body中数据
	 */
	private byte[] body;
	/**
	 * 用于存储请求参数
	 */
	private Map<String, String[]> params = new HashMap<>();

	/**
	 * header参数
	 */
	private Map<String, String> headerMap = new HashMap<>();

	/**
	 * add a header with given name and value
	 *
	 * @param name
	 * @param value
	 */
	public void addHeader(String name, String value) {
		headerMap.put(name, value);
	}

	@Override
	public String getHeader(String name) {
		String headerValue = super.getHeader(name);
		if (headerMap.containsKey(name)) {
			headerValue = headerMap.get(name);
		}
		return headerValue;
	}

	/**
	 * get the Header names
	 */
	@Override
	public Enumeration<String> getHeaderNames() {
		List<String> names = Collections.list(super.getHeaderNames());
		for (String name : headerMap.keySet()) {
			names.add(name);
		}
		return Collections.enumeration(names);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		List<String> values = Collections.list(super.getHeaders(name));
		if (headerMap.containsKey(name)) {
			values.add(headerMap.get(name));
		}
		return Collections.enumeration(values);
	}


	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = HttpHelper.getBodyString(request).getBytes(Charset.forName("UTF-8"));
		params.putAll(request.getParameterMap());
	}


	public void setParameter(String name, Object value) {
		if (value != null) {
			if (value instanceof String[]) {
				params.put(name, (String[]) value);
			} else if (value instanceof String) {
				params.put(name, new String[]{(String) value});
			} else {
				params.put(name, new String[]{String.valueOf(value)});
			}
		}
	}

	/**
	 * 重写getParameter，代表参数从当前类中的map获取
	 *
	 * @param name
	 * @return
	 */
	@Override
	public String getParameter(String name) {
		String[] values = params.get(name);
		if (values == null || values.length == 0) {
			return null;
		}
		return values[0];
	}

	/**
	 * 重写getParameterValues方法，从当前类的 map中取值
	 *
	 * @param name
	 * @return
	 */
	@Override
	public String[] getParameterValues(String name) {
		return params.get(name);
	}


	@Override
	public ServletInputStream getInputStream() throws IOException {

		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}

			@Override
			public int readLine(byte[] b, int off, int len) throws IOException {
				return super.readLine(b, off, len);
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}
		};
	}
}