package io.kanouken.admin.model.app;

import javax.persistence.MappedSuperclass;

import io.kanouken.admin.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 应用版本
 * @author xnq
 *
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
public class Version extends BaseEntity {
	private Integer appId;
	
	private String version;
	
	private String plat;
	
	private Byte updateNow;
	
	private String updateInfo;
}
