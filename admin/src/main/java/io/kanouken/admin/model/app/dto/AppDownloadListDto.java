package io.kanouken.admin.model.app.dto;

import lombok.Data;

@Data
public class AppDownloadListDto {
	private String name;
	private String icon;
	private String fileSize;

	private String updateTime;
	private String version;
	private String downloadUrl;
	
	private Integer appId;
}
