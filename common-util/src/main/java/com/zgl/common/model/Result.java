package com.zgl.common.model;

import com.zgl.common.enums.ErrCodeEnum;

import java.io.Serializable;

/**
 * 返回结果类
 * @author zgl
 * @date 2019/3/27 下午2:58
 */
public class Result<T> implements Serializable {
	private static final long serialVersionUID = -4745703790022338793L;

	private boolean rlt;

	private String message;

	private String code;

	private T data;

	public static final String SUCCESS = "000000";

	public static final String FAIL = "999999";

	public static final String BIZ_ERROR = "000001";

	public Result() {
	}

	public boolean isRlt() {
		return this.rlt;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return this.code;
	}

	public T getData() {
		return this.data;
	}

	public void setRlt(boolean rlt) {
		this.rlt = rlt;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Result)) {
			return false;
		} else {
			Result<?> other = (Result)o;
			if (!other.canEqual(this)) {
				return false;
			} else if (this.isRlt() != other.isRlt()) {
				return false;
			} else {
				label49: {
					Object this$message = this.getMessage();
					Object other$message = other.getMessage();
					if (this$message == null) {
						if (other$message == null) {
							break label49;
						}
					} else if (this$message.equals(other$message)) {
						break label49;
					}

					return false;
				}

				Object this$code = this.getCode();
				Object other$code = other.getCode();
				if (this$code == null) {
					if (other$code != null) {
						return false;
					}
				} else if (!this$code.equals(other$code)) {
					return false;
				}

				Object this$data = this.getData();
				Object other$data = other.getData();
				if (this$data == null) {
					if (other$data != null) {
						return false;
					}
				} else if (!this$data.equals(other$data)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof Result;
	}

	@Override
	public String toString() {
		return "Result(rlt=" + this.isRlt() + ", message=" + this.getMessage() + ", code=" + this.getCode() + ", data=" + this.getData() + ")";
	}
}