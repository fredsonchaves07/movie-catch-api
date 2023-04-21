package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fredsonchaves07.moviecatchapi.api.services.authentication.RecoveryPasswordApiService;
import com.fredsonchaves07.moviecatchapi.api.services.authentication.RecoveryPasswordByTokenApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recovery")
@Api(tags = {"Authentication"})
@Tag(name = "Authentication", description = "Recovery Password User Resource")
public class RecoveryUserApiResource {

    @Autowired
    private RecoveryPasswordApiService recoveryPasswordApiService;

    @Autowired
    private RecoveryPasswordByTokenApiService recoveryPasswordByTokenApiService;

    @PostMapping
    @ApiOperation(value = "Recovery password user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> recovery(
            @ApiParam(name = "Login user", value = "Login user credentials body")
            @RequestBody LoginDTO loginDTO
    ) {
        return ResponseEntity.ok(recoveryPasswordApiService.execute(loginDTO));
    }

    @GetMapping("/{token}")
    @ApiOperation(value = "Recovery user by token")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> recoveryByToken(@PathVariable TokenDTO token) {
        return ResponseEntity.ok(recoveryPasswordByTokenApiService.execute(token));
    }
}
