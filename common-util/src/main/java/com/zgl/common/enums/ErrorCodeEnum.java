package com.zgl.common.enums;

/**
 * @author zgl
 * @date 2019/8/20 下午5:26
 */
public enum ErrorCodeEnum {

	// system error
	SYS_SUCCESS("000000","success"),
	SYS_FAIL("999999","error"),
	SYS_PARAM_ERROR("900001","参数不合法"),
	SYS_NULL_ERROR("100002", "参数为空"),
	SYS_VALIDATE_ERROR("100003", "验证异常"),
	SYS_SIGN_ERROR("100004", "延签异常"),
	SYS_DATE_FORMAT_ERROR("100005", "日期格式错误"),
	SYS_CLASS_TYPE_ERROR("100006", "Java类不符合要求"),
	SYS_HTTP_SYSTEM_ERROR("100007", "接口系统异常"),
	SYS_RESULT_EMPTY_ERROR("100008", "数据为空"),

	// header error
	SYS_HEADER_EMP_IS_NULL("200000", "header empNo/creator is must"),
	SYS_HEADER_PLATFORM_ERROR("200001", "header platform is error"),
	SYS_HEADER_CITY_CODE_ERROR("200002", "header cityCode is null"),

	//sms code valid
	SMS_CODE_SEND_ERROR("300001", "短信验证码发送失败"),

	// common error
	COMMON_CITYCODE_NULL_ERROR("400001", "城市编码不能为空"),
	IDS_IS_NULL_ERROR("400002", "IDS不能为空"),
	COMMON_STUDENT_ID_NULL_ERROR("400003", "学生id不能为空"),
	COMMON_YEAR_NULL_ERROR("400004", "年份不能为空"),
	COMMON_CLASS_ID_NO_NULL("400005", "班级id不能为空"),
	// trade error
	TRADE_PARAM_ERROR("600001", "trade模块异常，参数不合法"),
	TRADE_REGIST_SUBMIT_ERROR("600002", "trade模块异常，提交报名错误"),
	TRADE_REGIST_RESULT_ERROR("600003", "trade模块异常，报名结果查询错误"),
	TRADE_CHANGECLASS_SUBMIT_ERROR("600004", "trade模块异常，提交转班错误"),
	TRADE_CHANGECLASS_RESULT_ERROR("600005", "trade模块异常，转班结果查询错误"),
	TRADE_GET_SAMECLASS_ERROR("600006", "trade模块异常，同期班查询错误"),

	// auth
	EMPLOYEE_DATA_ERROR("39000", "您没有业务系统权限无法正常操作，请联系管理员！"),
	// sign
	SIGN_CALCULATE_ERROR("40001", "数据加密出错"),
	CASHIER_INVALID_ERROR("40002", "收银台token失效"),
	CASHIER_PAY_END_ERROR("40003", "已成功支付"),

	//register
	COMMON_REGISTERID_IS_NULL("600007", "报名id不能为空"),

	FREEMARKER_GENERATE_HTML_ERROR("700001", "freemarker 生成html 错误"),
	;

	private String code;
	private String message;

	ErrorCodeEnum(String code, String message) {
		this.setCode(code);
		this.setMessage(message);
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
