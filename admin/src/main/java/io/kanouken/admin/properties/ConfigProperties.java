package io.kanouken.admin.properties;

import lombok.Data;

@Data
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
}
