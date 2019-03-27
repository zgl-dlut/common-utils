package com.zgl.common.model;

import com.zgl.common.enums.ErrCodeEnum;

/**
 * 返回结果类
 * @author zgl
 * @date 2019/3/27 下午2:58
 */
public class Result {
	private String code;

	private String message;

	private Object data;

	public Result() {
	}

	public Result(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public Result(ErrCodeEnum errCodeEnum) {
		this.code = errCodeEnum.getCode();
		this.message = errCodeEnum.getMessage();
	}

	public Result(ErrCodeEnum errCodeEnum, Object data) {
		this.code = errCodeEnum.getCode();
		this.message = errCodeEnum.getMessage();
		this.data = data;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static Result ok() {
		return new Result(ErrCodeEnum.SUCCESS);
	}

	public static Result ok(Object data) {
		return new Result(ErrCodeEnum.SUCCESS, data);
	}

	public static Result error() {
		return new Result(ErrCodeEnum.FAIL);
	}

	public static Result error(ErrCodeEnum errCodeEnum) {
		return new Result(errCodeEnum);
	}
}