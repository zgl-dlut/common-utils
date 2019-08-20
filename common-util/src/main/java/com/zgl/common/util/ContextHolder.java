package com.zgl.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author zgl
 * @date 2019/8/20 下午5:37
 */
public class ContextHolder {
	private static final ThreadLocal<String> platformHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> cityCodeHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empIdHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empNoHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empNameHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empDepHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<Integer> uidHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empServiceHolder = new TransmittableThreadLocal<>();

	private static final ThreadLocal<String> empServiceNameHolder = new TransmittableThreadLocal<>();


	public static Integer getUid() {
		return uidHolder.get();
	}

	public static void setUid(Integer uid) {
		uidHolder.set(uid);
	}

	public ContextHolder() {
	}

	public static String getEmpNo() {
		return empNoHolder.get();
	}

	public static void setEmpNo(String empNo) {
		empNoHolder.set(empNo);
	}

	public static String getEmpName() {
		return empNameHolder.get();
	}

	public static void setEmpName(String empName) {
		empNameHolder.set(empName);
	}

	public static String getPlatform() {
		return platformHolder.get();
	}

	public static void setPlatform(String platform) {
		platformHolder.set(platform);
	}

	public static void setCityCode(String cityCode) {
		cityCodeHolder.set(cityCode);
	}

	public static String getCityCode() {
		return cityCodeHolder.get();
	}

	public static String getEmpId() {
		return empIdHolder.get();
	}

	public static void setEmpId(String empId) {
		empIdHolder.set(empId);
	}

	public static String getEmpDep() {
		return empDepHolder.get();
	}

	public static void setEmpDep(String empEdp) {
		empDepHolder.set(empEdp);
	}

	public static String getEmpService() {
		return empServiceHolder.get();
	}

	public static void setEmpService(String empService) {
		empServiceHolder.set(empService);
	}

	public static String getEmpServiceName() {
		return empServiceNameHolder.get();
	}

	public static void setEmpServiceName(String empService) {
		empServiceNameHolder.set(empService);
	}

	public static void remove() {
		platformHolder.remove();
		cityCodeHolder.remove();
		empIdHolder.remove();
		empNoHolder.remove();
		empNameHolder.remove();
		empDepHolder.remove();
		uidHolder.remove();
		empServiceHolder.remove();
		empServiceNameHolder.remove();
	}
}