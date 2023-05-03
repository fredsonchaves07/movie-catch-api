package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.ManagementInfoUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UpdateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Api(tags = {"User"})
@Tag(name = "User", description = "User Resource")
public class ManagementInfoUserApiResource {

    @Autowired
    private ManagementInfoUserApiService service;

    @PutMapping()
    @ApiOperation(value = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> create(
            @ApiParam(name = "User", value = "User body parameter")
            @RequestBody UpdateUserDTO updateUserDTO
    ) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.execute(new UserDTO(null, email), updateUserDTO)
                );
    }
}
