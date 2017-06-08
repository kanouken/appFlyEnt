package io.kanouken.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.kanouken.admin.model.user.dto.CurrentAccountDto;
import lombok.Getter;
import lombok.Setter;

public class AbstractVo {
	@Setter
	@Getter
	@JsonIgnore
	private CurrentAccountDto currentAccount;
}
