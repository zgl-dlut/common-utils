package com.zgl.common.configuration;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.zgl.common.interceptor.AccessLogHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author zgl
 * @date 2019/3/27 下午2:55
 */
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private AccessLogHandler accessLogHandler;


	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return stringHttpMessageConverter;
	}


	/**
	 * 消息体转义
	 *
	 * @return\
	 */
	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		// 设置fastjson转义
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		{
			FastJsonConfig config = new FastJsonConfig();
			// 设置是否需要格式化
			config.setSerializerFeatures(SerializerFeature.PrettyFormat);
			config.setFeatures(Feature.OrderedField);
			config.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty);
			fastJsonHttpMessageConverter.setFastJsonConfig(config);
			List<MediaType> supportedMediaTypes = Lists.newArrayList();
			supportedMediaTypes.add(MediaType.APPLICATION_JSON);
			supportedMediaTypes.add(MediaType.TEXT_PLAIN);
			fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
			fastJsonHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
		}
		return fastJsonHttpMessageConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(accessLogHandler).addPathPatterns("/**");
	}
}