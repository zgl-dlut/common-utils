package com.zgl.common.interceptor;

import com.zgl.common.enums.ErrCodeEnum;
import com.zgl.common.exception.BusinessException;
import com.zgl.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.concurrent.CompletionException;

/**
 * 全局异常处理类
 * @author zgl
 * @date 2019/3/27 下午2:57
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * 参数校验错误异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		BindingResult bindingResult = e.getBindingResult();
		FieldError firstFieldError = bindingResult.getFieldErrors().get(0);
		return new Result();
	}

	/**
	 * 参数校验错误异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleConstraintViolationException(ConstraintViolationException e){
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		System.out.println(constraintViolations.toString());
		String message = constraintViolations.iterator().next().getMessage();
		return new Result();
	}

	/**
	 * 参数转换错误异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
		log.error("Unexpected error", e);
		return new Result();
}

	/**
	 * 参数校验错误异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleBindException(BindException e) {
		BindingResult bindingResult = e.getBindingResult();
		FieldError firstFieldError = bindingResult.getFieldErrors().get(0);
		return new Result();
	}

	/**
	 * 业务错误异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleBusinessException(BusinessException e) {
		ErrCodeEnum errCodeEnum = e.getErrCodeEnum();
		e.printStackTrace();//输出异常信息
		if (errCodeEnum != null) {
			return new Result();
		} else {
			return new Result();
		}
	}

	/**
	 * CompletionException异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.OK)
	public Result handleCompletionException(CompletionException e) {
		Throwable t = e.getCause();
		if (t instanceof BusinessException) {
			return handleBusinessException((BusinessException) t);
		}
		log.error("Unexpected error", e);
		return new Result();
	}

	/**
	 * 系统异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Result handleException(Exception e) {
		log.error("Unexpected error", e);
		return new Result();
	}
}