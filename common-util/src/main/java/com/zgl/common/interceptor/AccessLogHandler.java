package com.zgl.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zgl
 * @date 2019/3/27 下午2:53
 */
@Slf4j
@Component
public class AccessLogHandler implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//log.info("preHandle...........");
		long currentTime = System.currentTimeMillis();
		request.setAttribute("Current-Time", currentTime);
		log.info("{} {}", request.getMethod(), request.getRequestURI());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		//log.info("postHandle...........");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
		//log.info("afterCompletion...........");
		long currentTime = (long) request.getAttribute("Current-Time");
		log.info("{} has finished in {}ms", request.getRequestURI(), System.currentTimeMillis() - currentTime);
	}
}