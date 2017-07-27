package io.kanouken.admin.model.appversion.vo;

import lombok.Data;

@Data
public class CurrentVersionVo {
	private Integer appId;

	private String version;

	private String plat;

	private Byte updateNow;

	private String updateInfo;
	private String downloadUrl;

}
