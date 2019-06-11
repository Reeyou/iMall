package com.reeyou.imall.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Reeyou
 * @data 2019/5/20 15:54
 */
public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

	private static Properties properties;

	//使用util.properties读取配置属性
	static {
		String fileName = "FTP.properties";
		properties = new Properties();
		try {
			//字符流读取文件
			properties.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
		} catch (IOException e) {
			logger.error("配置文件读取异常", e);
		}
	}

	public static String getProperty(String key) {
		String value = properties.getProperty(key.trim());
		if(StringUtils.isBlank(value)) {
			return null;
		}
		return value.trim();
	}

	public static String getProperty(String key, String defaultValue) {
		String value = properties.getProperty(key.trim());
		if(StringUtils.isBlank(value)) {
			value = defaultValue;
		}
		return value.trim();
	}


}
