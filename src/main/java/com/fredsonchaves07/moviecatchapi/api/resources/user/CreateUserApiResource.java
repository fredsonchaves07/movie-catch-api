package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.api.services.user.CreateUserAPIService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@Api(tags = {"User"})
@Tag(name = "User", description = "User Resource")
public class CreateUserApiResource {

    @Autowired
    private CreateUserAPIService createUserAPIService;

    @PostMapping()
    @ApiOperation(value = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad request")
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserDTO> create(
            @ApiParam(name = "User", value = "User body parameter")
            @RequestBody CreateUserDTO createUserDTO
    ) {
        try {
            UserDTO userDTO = createUserAPIService.execute(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (CreateUserUseCaseException exception) {
            throw new BadRequestException(exception.getType(), exception.getTitle(), exception.getMessage());
        }
    }
}
