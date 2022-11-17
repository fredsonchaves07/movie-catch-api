package com.fredsonchaves07.moviecatchapi.api.config;

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

import static java.util.Collections.singletonList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalResponses(
                        HttpMethod.POST,
                        singletonList(
                                new ResponseBuilder()
                                        .code("500")
                                        .description("Server Error")
                                        .build()
                        )
                )
//                .globalResponses(HttpMethod.GET,
//                        singletonList(new ResponseBuilder()
//                                .code("500")
//                                .description("Server error")
//                                .representation(MediaType.APPLICATION_JSON)
//                                .apply(r ->
//                                        r.model(m ->
//                                                m.referenceModel(ref ->
//                                                        ref.key(k ->
//                                                                k.qualifiedModelName(q ->
//                                                                        q.namespace("some:namespace")
//                                                                                .name("ERROR"))))))
//                                .code("405")
//                                .description("Method not allowed")
//                                .representation(MediaType.APPLICATION_JSON)
//                                .apply(r ->
//                                        r.model(m ->
//                                                m.referenceModel(ref ->
//                                                        ref.key(k ->
//                                                                k.qualifiedModelName(q ->
//                                                                        q.namespace("some:namespace")
//                                                                                .name("ERROR"))))))
//                                .code("404")
//                                .description("Resource not found")
//                                .representation(MediaType.APPLICATION_JSON)
//                                .apply(r ->
//                                        r.model(m ->
//                                                m.referenceModel(ref ->
//                                                        ref.key(k ->
//                                                                k.qualifiedModelName(q ->
//                                                                        q.namespace("some:namespace")
//                                                                                .name("ERROR"))))))
//                                .build()))
//                .globalResponses(HttpMethod.POST,
//                        singletonList(new ResponseBuilder()
//                                .code("500")
//                                .description("Server error")
//                                .representation(MediaType.APPLICATION_JSON)
//                                .apply(r ->
//                                        r.model(m ->
//                                                m.referenceModel(ref ->
//                                                        ref.key(k ->
//                                                                k.qualifiedModelName(q ->
//                                                                        q.namespace("some:namespace")
//                                                                                .name("ERROR"))))))
//                                .code("405")
//                                .description("Method not allowed")
//                                .representation(MediaType.APPLICATION_JSON)
//                                .apply(r ->
//                                        r.model(m ->
//                                                m.referenceModel(ref ->
//                                                        ref.key(k ->
//                                                                k.qualifiedModelName(q ->
//                                                                        q.namespace("some:namespace")
//                                                                                .name("ERROR"))))))
//                                .build()))
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
