package io.kanouken.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import io.kanouken.admin.model.user.dto.CurrentAccountDto;

public abstract class AbstractController {
	protected CurrentAccountDto  currentAccount;
	
	@Autowired
	protected HttpServletRequest  request;
	
	protected abstract  void setCurrentAccount(CurrentAccountDto dto);
}
