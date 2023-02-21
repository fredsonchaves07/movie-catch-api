package com.fredsonchaves07.moviecatchapi.api.resources.authentication;

import com.fredsonchaves07.moviecatchapi.api.services.authentication.AuthenticateUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.authentication.LoginDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationApiResource {

    @Autowired
    AuthenticateUserApiService service;

    @PostMapping
    public ResponseEntity<TokenDTO> authenticate(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(service.execute(loginDTO));
    }
}
