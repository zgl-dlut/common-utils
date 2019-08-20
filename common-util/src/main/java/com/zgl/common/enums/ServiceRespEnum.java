package com.zgl.common.enums;

/**
 * @author zgl
 * @date 2019/8/20 下午5:22
 */
public enum ServiceRespEnum {


	/**
	 * student
	 */
	STUDENT_SERVICE_RESP("学生服务响应结果", "code", "message", "data", "000000", "999999"),

	/**
	 * register
	 */
	ORDER_SERVICE_RESP("订单服务响应结果", "code", "message", "data", "000000", "999999"),

	/**
	 * register regist
	 */
	ORDER_REGIST_SERVICE_RESP("订单报名接口成功响应结果", "code", "message", "data", "10001", "999999"),

	REFUND_TRADE_SERVICE_RESP("订单退费接口成功相应结果", "code", "message", "data", "000000", "999999"),

	/**
	 * clazz
	 */
	CLAZZ_SERVICE_RESP("四代clazz服务响应结果", "code", "message", "data", "000000", "999999"),

	COURSE_API_SERVICE_RESP("班课API服务响应结果", "code", "message", "data", "000000", "999999"),

	/**
	 * baseData
	 */
	BASE_DATA_SERVICE_RESP("基础数据服务响应结果", "code", "message", "data", "000000", "999999"),

	/**
	 * baseData
	 */
	TEACHER_SERVICE_RESP("教师中心服务响应结果", "code", "message", "data", "10000", "999999"),

	/**
	 * attendance
	 */
	ATTENDANCE_SERVICE_RESP("考勤中心服务响应结果", "success", "message", "data", "true", "999999"),

	/**
	 * class_attendance
	 */
	CLASS_ATTENDANCE_SERVICE_RESP("考勤中心服务响应结果", "success", "message", "data", "true", "999999"),
	/**
	 * 学员成绩
	 */
	STUDENT_PAPER_SERVICE_RESP("学员成绩服务响应结果", "rlt", "msg", "data", "true", "999999"),
	/**
	 * coupon1
	 */
	COUPON_SERVICE_RESP("优惠券服务响应结果", "code", "msg", "coupon", "00000", "999999"),
	/**
	 * coupon2
	 */
	COUPON_APPLICABLE_SERVICE_RESP("优惠券适用班级服务查询响应结果", "code", "message", "data", "00000", "999999"),


	FINANCE_SERVICE_RESP("财务服务响应结果", "code", "messages", "data", "200", "999999"),

	FINANCE_NATIONAL_SERVICE_RESP("财务服务响应结果", "code", "message", "data", "20000", "000000"),

	FINANCE_INVOICE_SERVICE_RESP("财务服务响应结果", "code", "message", "data", "20000", "100003"),

	FINANCE_REFUND_SERVICE_RESP("财务服务响应结果", "code", "messages", "data", "200", "999999"),

	USER_SERVICE_RESP("用户中心服务响应结果", "code", "message", "data", "10000", "999999"),

	USER_OTHER_SERVICE_RESP("用户中心服务响应结果", "code", "message", "data", "000000", "999999"),

	CALL_CENTER_SERVICE_RESP("呼叫平台服务响应结果", "code", "message", "data", "00000", "999999"),

	MESSAGE_CENTER_SERVICE_RESP("通知中心响应结果", "code", "errMsg", "success", "000000", "999999"),
	/**
	 * ips
	 */
	IPS_SERVICE_RESP("ips服务响应结果", "code", "message", "data", "000000", "999999"),;


	private String desc;

	private String codeFieldName;

	private String msgFieldName;

	private String dataFieldName;

	private Object successCode;

	private Object systemErrorCode;

	ServiceRespEnum(String desc, String codeFieldName, String msgFieldName, String dataFieldName, Object successCode, Object systemErrorCode) {
		this.desc = desc;
		this.codeFieldName = codeFieldName;
		this.msgFieldName = msgFieldName;
		this.dataFieldName = dataFieldName;
		this.successCode = successCode;
		this.systemErrorCode = systemErrorCode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCodeFieldName() {
		return codeFieldName;
	}

	public void setCodeFieldName(String codeFieldName) {
		this.codeFieldName = codeFieldName;
	}

	public String getMsgFieldName() {
		return msgFieldName;
	}

	public void setMsgFieldName(String msgFieldName) {
		this.msgFieldName = msgFieldName;
	}

	public String getDataFieldName() {
		return dataFieldName;
	}

	public void setDataFieldName(String dataFieldName) {
		this.dataFieldName = dataFieldName;
	}

	public Object getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(Object successCode) {
		this.successCode = successCode;
	}

	public Object getSystemErrorCode() {
		return systemErrorCode;
	}

	public void setSystemErrorCode(Object systemErrorCode) {
		this.systemErrorCode = systemErrorCode;
	}
}