package com.zgl.common.enums;

/**
 * @author zgl
 * @date 2019/3/27 上午10:19
 */
public enum ErrCodeEnum {
	/**
	 * 请求成功
	 */
	SUCCESS("00000", "请求成功"),
	/**
	 * 请求参数错误
	 */
	REQUEST_PARAM_ERROR("10000", "请求参数错误"),
	/**
	 * 请求业务系统异常
	 */
	REQUEST_BUSINESS_SERVICE_ERROR("20000", "请求业务系统异常"),
	/**
	 * 系统错误
	 */
	FAIL("99999", "系统错误"),;
	private String code;

	private String message;

	ErrCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
