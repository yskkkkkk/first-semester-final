package com.ssafy.happyhouse.config;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost/swagger-ui.html
	
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private String version = "V1";
	private String title = "HappyHouse " + version;
	
	@Bean
	public Docket postsApi() {
//		List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
//		responseMessages.add(new ResponseMessageBuilder().code(200).message("OK !!!").build());
//		responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생 !!!").responseModel(new ModelRef("Error")).build());
//		responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다 !!!").build());
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("ssafyVueAPI")
                .consumes(getConsumeContentTypes())
                .produces(getProduceContentTypes())
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(postPaths())
				.build(); 
//				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET,responseMessages);
	}
	
	private Predicate<String> postPaths() {
		return PathSelectors.any();
//		return PathSelectors.ant("/**")
//		return or(regex("/user/.*"), regex("/article/.*"), regex("/memo/.*"));
//		return regex("/admin/.*");
	}

    private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
				.description("8조 FINAL PROJECT/ 김도연, 류대성")
//				.termsOfServiceUrl("https://edu.ssafy.com")
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
				.license("SSAFY License")
				.licenseUrl("ssafy@ssafy.com").version("1.0").build();
	}

}


