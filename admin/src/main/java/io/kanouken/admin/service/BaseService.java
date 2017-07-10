package io.kanouken.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.kanouken.admin.properties.ConfigProperties;
@Component
public abstract class BaseService {
	
	@Autowired
	protected ConfigProperties  config;
	
	@Value("${server.context-path}")
	protected String ctxPath;
	
}
