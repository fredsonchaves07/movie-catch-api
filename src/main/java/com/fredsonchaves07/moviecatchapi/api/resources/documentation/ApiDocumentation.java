package com.fredsonchaves07.moviecatchapi.api.resources.documentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class ApiDocumentation {

    @GetMapping("/docs")
    public void apiDocumentation(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/");
    }
}
