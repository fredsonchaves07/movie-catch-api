package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.ConfirmUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/confirm")
public class ConfirmUserApiResource {

    @Autowired
    private ConfirmUserApiService confirmUserApiService;

    @PutMapping("/{token}")
    public ResponseEntity<UserDTO> confirmUser(@PathVariable String token) {
        UserDTO userDTO = confirmUserApiService.execute(token);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
