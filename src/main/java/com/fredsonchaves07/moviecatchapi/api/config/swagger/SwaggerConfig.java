package com.fredsonchaves07.moviecatchapi.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fredsonchaves07.moviecatchapi.api.resources"))
                .paths(PathSelectors.any())
                .build()
                .globalResponses(
                        HttpMethod.GET,
                        Arrays.asList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build(),
                                new ResponseBuilder()
                                        .code("405")
                                        .description("Method not allowed")
                                        .build(),
                                new ResponseBuilder()
                                        .code("404")
                                        .description("Resource not found")
                                        .build()
                        )
                )
                .globalResponses(
                        HttpMethod.POST,
                        Arrays.asList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build(),
                                new ResponseBuilder()
                                        .code("405")
                                        .description("Method not allowed")
                                        .build()
                        )
                )
                .globalResponses(
                        HttpMethod.PUT,
                        Arrays.asList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build(),
                                new ResponseBuilder()
                                        .code("405")
                                        .description("Method not allowed")
                                        .build(),
                                new ResponseBuilder()
                                        .code("404")
                                        .description("Resource not found")
                                        .build()
                        )
                )
                .globalResponses(
                        HttpMethod.DELETE,
                        Arrays.asList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build(),
                                new ResponseBuilder()
                                        .code("405")
                                        .description("Method not allowed")
                                        .build(),
                                new ResponseBuilder()
                                        .code("404")
                                        .description("Resource not found")
                                        .build()
                        )
                )
                .globalResponses(
                        HttpMethod.PATCH,
                        Arrays.asList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build(),
                                new ResponseBuilder()
                                        .code("405")
                                        .description("Method not allowed")
                                        .build(),
                                new ResponseBuilder()
                                        .code("404")
                                        .description("Resource not found")
                                        .build()
                        )
                )
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Movie Catch Api")
                .description("Movice catch api documentation")
                .version("1.2.2")
                .license("MIT")
                .licenseUrl("https://github.com/fredsonchaves07/movie-catch-api/blob/main/LICENSE")
                .contact(new Contact("Dev Team", "", "fredsonchaves07@gmail.com"))
                .build();
    }
}
