package io.kanouken.admin.model.appversion.vo;

import lombok.Data;

@Data
public class VersionDetailVo {

	private String appId;
	private String appName;
	private String version;
	private String plat;
	private String updateNow;
	private String updateInfo;

	private String createTime;
	private String creator;

	private String downloadUrl;

	private String isTimeToUpdate;
	private String timeToUpdate;
}
