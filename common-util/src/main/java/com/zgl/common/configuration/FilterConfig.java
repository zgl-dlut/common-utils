package com.zgl.common.configuration;

import com.zgl.common.filter.MyFilter;
import com.zgl.common.filter.SecondFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zgl
 * @date 2019/3/27 下午2:01
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean filterRegisterA() {
		FilterRegistrationBean frBean = new FilterRegistrationBean();
		frBean.setFilter(new MyFilter());
		frBean.setOrder(1);
		frBean.addUrlPatterns("/*");
		return frBean;
	}

	@Bean
	public FilterRegistrationBean filterRegister() {
		FilterRegistrationBean frBean = new FilterRegistrationBean();
		frBean.setFilter(new SecondFilter());
		frBean.setOrder(2);
		frBean.addUrlPatterns("/*");
		return frBean;
	}
}