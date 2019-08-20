package com.zgl.common;

/**
 * @author zgl
 * @date 2019/8/20 下午5:28
 */
public class Constants {

	/**
	 * 逗号分隔符
	 */
	public static final String SEPARATE_COMMA = ",";

	/**
	 * 双引号空
	 */
	public static final String SEPARATE = "";

	/**
	 * 空格
	 */
	public static final String SEPARATE_SPACE = " ";

	/**
	 * 横杠
	 */
	public static final String SEPARATE_HORIZONTAL = "-";

	/**
	 * 竖杠
	 */
	public static final String SEPARATE_VERTICAL = "|";

	/**
	 * 分号
	 */
	public static final String SEPARATE_SEMICOLON = ";";

	/**
	 * 点号
	 */
	public static final String SEPARATE_SPOT = ".";

	/**
	 * 默认页码
	 */
	public static final Integer DEFAULT_PAGE_INDEX = 1;

	/**
	 * 默认每页大小
	 */
	public static final Integer DEFAULT_PAGE_SIZE= 10;

	/**
	 * 默认每页大小
	 */
	public static final Integer DEFAULT_TOTAL_COUNT= 10;

	//签名算法HmacSha256
	public static final String HMAC_SHA256 = "HmacSHA256";
	//编码UTF-8
	public static final String ENCODING = "UTF-8";
	//UserAgent
	public static final String USER_AGENT = "demo/aliyun/java";
	//换行符
	public static final String LF = "\n";
	//串联符
	public static final String SPE1 = ",";
	//示意符
	public static final String SPE2 = ":";
	//连接符
	public static final String SPE3 = "&";
	//赋值符
	public static final String SPE4 = "=";
	//问号符
	public static final String SPE5 = "?";
	//默认请求超时时间,单位毫秒
	public static final int DEFAULT_TIMEOUT = 1000;
	//参与签名的系统Header前缀,只有指定前缀的Header才会参与到签名中
	public static final String CA_HEADER_TO_SIGN_PREFIX_SYSTEM = "X-Ca-";

	public static final String EXCLUDES="excludes";

}