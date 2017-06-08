package io.kanouken.admin.controller;

import java.util.HashMap;
import java.util.Map;

import io.kanouken.admin.model.foo.Foo;
import io.kanouken.admin.model.user.dto.CurrentAccountDto;

public class BaseController extends AbstractController {
	
	
	
	
	
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
