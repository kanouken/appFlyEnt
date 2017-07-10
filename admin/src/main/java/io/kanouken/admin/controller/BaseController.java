package io.kanouken.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import io.kanouken.admin.model.foo.Foo;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;
import io.kanouken.admin.properties.ConfigProperties;

public class BaseController extends AbstractController {
	
	@Autowired
	protected ConfigProperties config;
	
	
	
	@Override
	protected void setCurrentAccount(CurrentAccountDto dto) {
		
		this.currentAccount = dto;
	}
	
	
	
	protected <T> Map<String, Object> renderDt(Iterable<T> objs) {
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("aaData", objs);
		return tmp;
	}
	
	

}
