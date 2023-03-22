package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fredsonchaves07.moviecatchapi.api.services.authentication.AuthenticateUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@Api(tags = {"Authentication"})
@Tag(name = "Authentication", description = "Authentication Resource")
public class AuthenticationApiResource {

    @Autowired
    AuthenticateUserApiService service;

    @PostMapping
    @ApiOperation(value = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<TokenDTO> authenticate(
            @ApiParam(name = "Login", value = "User credentials login body")
            @RequestBody LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(service.execute(loginDTO));
    }
}
