package io.kanouken.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.kanouken.admin.interceptor.AuthCheckInterceptor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application extends WebMvcConfigurerAdapter {

	public void addInterceptors(InterceptorRegistry registry) {
		AuthCheckInterceptor auth = new AuthCheckInterceptor();
		registry.addInterceptor(auth).addPathPatterns("/**").excludePathPatterns("/webjars/**", "/swagger**/**",
				"/configuration/**", "/v2/api**", "/info", "/report/*/export",
				"/report/cXiangMuReport/projectDetail/export", "/api/users/login", "/web/user/login");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(Feature.WRITE_NUMBERS_AS_STRINGS, true);
		om.configure(Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		om.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp)
					throws IOException, JsonProcessingException {
				jg.writeString("");
			}
		});
		return om;
	}

	@Bean
	public Docket createRestApi() {

		List<ApiKey> sList = new ArrayList<ApiKey>();
		sList.add(apiKey());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(testApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("io.kanouken.admin.controller")).paths(PathSelectors.any()).build()
				.securitySchemes(sList);
	}

	private ApiInfo testApiInfo() {
		Contact contact = new Contact("auth", "name", "email");
		ApiInfo apiInfo = new ApiInfo("", // 大标题
				"api接口", // 小标题
				"0.1", // 版本
				"", contact, // 作者
				"", // 链接显示文字
				""// 网站链接
		);
		return apiInfo;
	}

	private ApiKey apiKey() {
		return new ApiKey("api_key", "api_key", "header");
	}
}
