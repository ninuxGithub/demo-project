package com.example.demo.config;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * 启动浪费时间---注释掉
* @ClassName: com.example.demo.config.Swagger2Config 
* @Description: TODO(--)
* @date 2017年7月27日 上午11:26:35
 */
//@Configuration
//@EnableSwagger2
public class Swagger2Config {

	/**
	 * 通过swagger2 扫描对应的package目录下controller Restful API, 方便用户调用
	 * 
	 * @return
	 */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("使用swagger2构建Restfil API")
                .description("springboot")
                .termsOfServiceUrl("https://github.com/")
                .contact("ninuxGihub")
                .version("1.0")
                .build();
    }
}
