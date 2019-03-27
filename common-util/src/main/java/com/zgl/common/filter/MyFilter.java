package com.zgl.common.filter;


import com.alibaba.fastjson.JSONObject;
import com.zgl.common.util.MyHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zgl
 * @date 2019/3/27 上午11:57
 */
@Slf4j
public class MyFilter implements Filter {

	@Override
	public void destroy() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		MyHttpServletRequestWrapper requestWrapper = new MyHttpServletRequestWrapper(req);

		/**
		 * 修改body
		 */
		byte[] body = requestWrapper.getBody();
		String jsonString = new String(body);
		JSONObject json = JSONObject.parseObject(jsonString);
		String phoneNumber = json.getString("phoneNumbers");
		json.put("phoneNumbers", "111111111");
		body = json.toString().getBytes();
		requestWrapper.setBody(body);

		/**
		 * 修改header和param
		 */
		String name = requestWrapper.getParameter("name");
		String header = requestWrapper.getHeader("type");
		System.out.println("=====================" + header);
		requestWrapper.addHeader("type", "12345");
		requestWrapper.setParameter("name", name.toUpperCase());
		log.info("第一个过滤器执行+++++++++++++++++++,{}", this.getClass().getName());
		arg2.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}
}