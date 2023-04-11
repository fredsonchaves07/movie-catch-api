package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.ChangeRequestPasswordUserApiService;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/change")
@Api(tags = {"User"})
@Tag(name = "User", description = "User Resource")
public class ChangeRequestPasswordUserApiResource {

    @Autowired
    private ChangeRequestPasswordUserApiService service;

    @GetMapping
    @ApiOperation(value = "Change password user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Object> execute(
            @ApiParam(name = "Email", value = "User email")
            @RequestBody String email
    ) {
        service.execute(email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
