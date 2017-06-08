package io.kanouken.admin.model.app.vo;

import io.kanouken.admin.model.vo.AbstractVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AppAddVo extends AbstractVo{
	private String name;
	private String description;
	
	private String appIcon;
	private String keywords;
	private Integer customerId;
	private String customerName;
	private String appId;
}
