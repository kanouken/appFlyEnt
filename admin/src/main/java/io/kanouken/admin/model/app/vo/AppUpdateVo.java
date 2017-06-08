package io.kanouken.admin.model.app.vo;

import lombok.Data;

@Data
public class AppUpdateVo {
	private Integer id;
	private String name;
	private String description;
	private String appIcon;
	private String keywords;
	private Integer customerId;
	private String customerName;
	private String appId;
}
