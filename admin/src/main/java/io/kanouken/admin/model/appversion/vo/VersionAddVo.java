package io.kanouken.admin.model.appversion.vo;

import lombok.Data;

@Data
public class VersionAddVo {

	private String plat;

	private Byte updateNow;

	private String updateInfo;

	private String version;

	private Integer appId;
}
