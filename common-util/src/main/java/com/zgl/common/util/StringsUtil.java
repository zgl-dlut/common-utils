package com.zgl.common.util;

import com.zgl.common.enums.ErrorCodeEnum;
import com.zgl.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author zgl
 * @date 2019/8/20 下午5:27
 */
@Slf4j
public class StringsUtil {


	private static final Integer BATCH_MAX_SIZE = 200;

	public static final String VERTICAL_LINE_SEPARATE = "\\|";

	/**
	 * 初始化集合
	 * @param strs
	 * @param separate
	 * @return
	 */
	public static List<String> initStringList(String strs, String separate) {
		return initStringList(strs, separate, BATCH_MAX_SIZE);
	}

	/**
	 * 初始化集合
	 * @param strs
	 * @param separate
	 * @return
	 */
	public static List<String> initStringList(String strs, String separate, Integer maxSize) {
		if (!StringUtils.isNoneBlank(strs, separate)) {
			throw new BusinessException(ErrorCodeEnum.SYS_PARAM_ERROR);
		}

		String ids = strs.replaceAll(Constants.SEPARATE_SPACE, Constants.SEPARATE).trim();
		String [] idLen = ids.split(separate);
		if (idLen == null || idLen.length < 1) {
			log.error("initIdList idLen is null.");
			throw new BusinessException(ErrorCodeEnum.IDS_IS_NULL_ERROR);
		}

		List<String> idList = new ArrayList<String>();
		for (String id : idLen) {
			if (StringUtils.isEmpty(id) || StringUtils.isEmpty(id.trim())) {
				continue;
			}
			idList.add(id.trim());
		}

		if (maxSize == null || maxSize == 0) {
			maxSize = BATCH_MAX_SIZE;
		}

		if (CollectionUtils.isEmpty(idList)) {
			log.error("initIdList idLen is null");
			throw new BusinessException(ErrorCodeEnum.IDS_IS_NULL_ERROR);
		}

		if (idList.size() > maxSize) {
			log.error("initIdList idLen size > {}", maxSize);
			throw new BusinessException(ErrorCodeEnum.IDS_IS_NULL_ERROR);
		}

		return idList;
	}

	/**
	 * 初始化数组
	 * @param strs
	 * @param separate
	 * @return
	 */
	public static String [] initStringLen(String strs, String separate) {
		return initStringLen(strs, separate, BATCH_MAX_SIZE);
	}

	/**
	 * 初始化数组
	 * @param strs
	 * @param separate
	 * @return
	 */
	public static String [] initStringLen(String strs, String separate, Integer maxSize) {
		if (!StringUtils.isNoneBlank(strs, separate)) {
			throw new BusinessException(ErrorCodeEnum.SYS_PARAM_ERROR);
		}

		String ids = strs.replaceAll(Constants.SEPARATE_SPACE, Constants.SEPARATE).trim();
		String [] idLen = ids.split(separate);
		if (idLen == null || idLen.length < 1) {
			log.error("initIdList idLen is null.");
			throw new BusinessException(ErrorCodeEnum.IDS_IS_NULL_ERROR);
		}

		if (idLen.length > maxSize) {
			log.error("initIdList idLen size > {}", maxSize);
			throw new BusinessException(ErrorCodeEnum.IDS_IS_NULL_ERROR);
		}

		return idLen;
	}

	/**
	 * 获得数组的全组合（不区分顺序）
	 * @param len
	 * @return
	 */
	public static List<List<String>> getGroupList(String [] len) {
		List<List<String>> list = new ArrayList<>();
		if (len == null || len.length < 1) {
			return list;
		}

		int nCnt = len.length;

		int nBit = (0xFFFFFFFF >>> (32 - nCnt));

		for (int i = 1; i <= nBit; i++) {
			List<String> listTemp = new ArrayList<>();
			for (int j = 0; j < nCnt; j++) {
				if ((i << (31 - j)) >> 31 == -1) {
					listTemp.add(len[j]);
				}
			}
			list.add(listTemp);
		}
		return list;
	}

	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 错误消息拼接
	 * @param pre
	 * @param args
	 * @return
	 */
	public static String getErrorMessage(String pre, Object... args) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(pre);
		stringBuilder.append(";");
		for (Object errArg : args) {
			stringBuilder.append(errArg).append(",");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		return stringBuilder.toString();
	}

	//首字母转小写
	public static String toLowerCaseFirstOne(String s){
		if(Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	public static String getDoubleToString(Number value) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value);
	}
}