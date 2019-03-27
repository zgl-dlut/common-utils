package com.zgl.common.exception;

import com.zgl.common.enums.ErrCodeEnum;

/**
 * Business异常，业务逻辑层中当不满足某个条件时，抛出该异常，该异常相当于方法的第二个返回值
 * @author zgl
 * @date 2019/3/27 上午10:18
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 6852927784516859584L;

	/**
	 * 错误码
	 */
	private String code;

	/**
	 * 错误消息
	 */
	private String message;

	/**
	 * 错误枚举
	 */
	private ErrCodeEnum errCodeEnum;

	public BusinessException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public BusinessException(ErrCodeEnum errCodeEnum) {
		this.errCodeEnum = errCodeEnum;
	}
	public BusinessException(){}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrCodeEnum getErrCodeEnum() {
		return errCodeEnum;
	}

	public void setErrCodeEnum(ErrCodeEnum errCodeEnum) {
		this.errCodeEnum = errCodeEnum;
	}
}