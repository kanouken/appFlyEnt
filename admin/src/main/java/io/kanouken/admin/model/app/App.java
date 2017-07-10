package io.kanouken.admin.model.app;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.kanouken.admin.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class App extends BaseEntity {
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String description;
	@Column(nullable = false)
	private String icon;
	@Column(nullable = false, unique = true)
	private String keywords;
	@Column(nullable = false)
	private String appId;
	@Column(nullable = false)
	private Integer customerId;
	@Column(nullable = false)
	private String customerName;
	@Column(nullable = false)
	private String customerPrefix;

}
