package com.zgl.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author zgl
 * @date 2020/3/22 上午11:47
 */
@Slf4j
public class InetAddressUtils {
	public static String hostAddress() {
		// 获取操作系统类型
		String sysType = System.getProperties().getProperty("os.name");
		String ip;
		if (sysType.toLowerCase().startsWith("win")) {  // 如果是Windows系统，获取本地IP地址
			String localIP = null;
			try {
				localIP = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			if (localIP != null) {
				return localIP;
			}
		} else if (sysType.toLowerCase().startsWith("mac")) {
			ip = getIpByEthNum("en0"); // 兼容Mac book
			if (ip != null) {
				return ip;
			}
		} else {
			ip = getIpByEthNum("eth0"); // 兼容Linux
			if (ip != null) {
				return ip;
			}
		}
		return null;
	}

	/**
	 * 根据网络接口获取IP地址
	 *
	 * @param ethNum 网络接口名，Linux下是eth0
	 * @return
	 */
	private static String getIpByEthNum(String ethNum) {
		try {
			Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				if (ethNum.equals(netInterface.getName())) {
					Enumeration addresses = netInterface.getInetAddresses();
					while (addresses.hasMoreElements()) {
						ip = (InetAddress) addresses.nextElement();
						if (ip != null && ip instanceof Inet4Address) {
							return ip.getHostAddress();
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "获取服务器IP错误";
	}
}