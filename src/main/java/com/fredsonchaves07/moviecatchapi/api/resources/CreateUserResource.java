package com.fredsonchaves07.moviecatchapi.api.resources;

import com.fredsonchaves07.moviecatchapi.api.resources.exception.BadRequestException;
import com.fredsonchaves07.moviecatchapi.api.services.exception.CreateUserUseCaseException;
import com.fredsonchaves07.moviecatchapi.api.services.user.CreateUserAPIService;
import com.fredsonchaves07.moviecatchapi.domain.dto.CreateUserDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class CreateUserResource {

    @Autowired
    private CreateUserAPIService createUserAPIService;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO createUserDTO) {
        try {
            UserDTO userDTO = createUserAPIService.execute(createUserDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (CreateUserUseCaseException exception) {
            throw new BadRequestException(exception.getMessage());
        }
    }
}
