package io.kanouken.admin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "app")
public class ConfigProperties {
	/**
	 * plist 文件存放路径
	 */
	private String plistFilePath;
	
	/**
	 * 应用 包存放路径
	 */
	private String appFilePath;
	
	private Boolean backupEnable;
	/**
	 * 应用图标路径
	 */
	private String  iconPath;
	/**
	 * 公网文件下载目录
	 */
	private String  publicUrl;
}
