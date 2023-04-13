package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.ChangeRequestPasswordUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.EmailDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @GetMapping()
    @ApiOperation(value = "Change request password user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> execute(@RequestBody EmailDTO email) {
        service.execute(email);
        return ResponseEntity.noContent().build();
    }
}
