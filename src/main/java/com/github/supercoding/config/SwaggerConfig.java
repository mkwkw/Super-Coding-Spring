package com.github.supercoding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //어디에 있는 컨트롤러를 자동으로 인식할 것인가 - copy reference해서 경로 넣기
                .apis(RequestHandlerSelectors.basePackage("com.github.supercoding.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
