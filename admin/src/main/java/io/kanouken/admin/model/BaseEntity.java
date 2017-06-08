package io.kanouken.admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Data;

import java.util.*;

@MappedSuperclass
@Data
public class BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	
	@Column(nullable = false)
	private Date createTime;
	@Column(nullable = false)
	private Date updateTime;

	private String creator;
	private Long createId;

	private Long updateId;
	
	private Byte isDelete;
	
	@PrePersist
	protected void prePersist(){
		this.createTime = new Date();
		this.updateTime = new Date();
		this.isDelete = Byte.parseByte("0");
				
	}
	@PreUpdate
	protected void preUpdate(){
		this.updateTime  = new Date();
	}
}