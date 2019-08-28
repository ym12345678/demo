package com.twwin.base.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private boolean switchOn = true;

    @Bean
    public Docket createRestApi() {
        List<ResponseMessage> responseMessageList = new ArrayList<>();
        Predicate<RequestHandler> predicate;
        ApiInfo apiInfo;
        if (switchOn) {
            predicate = input -> input.findControllerAnnotation(Api.class).isPresent();
            apiInfo = buildApiInfo();
        } else {
            predicate = input -> false;
            apiInfo = new ApiInfoBuilder().build();
        }
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .apiInfo(apiInfo)
                .useDefaultResponseMessages(false)
                .select()
                .apis(predicate::test)
                .build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("服务列表")
                .description("")
                .version("1.0")
                .license("License By Twwin")
                .licenseUrl("")
                .build();
    }
}
