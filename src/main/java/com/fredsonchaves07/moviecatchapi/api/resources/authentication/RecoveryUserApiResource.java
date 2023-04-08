package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fredsonchaves07.moviecatchapi.api.services.authentication.RecoveryPasswordApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.RecoveryPasswordDTO;
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
    private RecoveryPasswordApiService service;

    @PostMapping
    @ApiOperation(value = "Recovery password user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 401, message = "Unauthorized")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> recovery(
            @ApiParam(name = "Recovery Password", value = "New user credentials login body")
            @RequestBody RecoveryPasswordDTO recoveryPasswordDTO
    ) {
        return ResponseEntity.ok(service.execute(recoveryPasswordDTO));
    }
}
