package com.fredsonchaves07.moviecatchapi.api.services;

import com.fredsonchaves07.moviecatchapi.api.services.token.JwtTokenService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtTokenServiceTest {

    private TokenService tokenService = new JwtTokenService();

    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        userDTO = userDTO();
    }

    @Test
    public void shouldEncryptToken() {
        assertNotNull(tokenService.encrypt(userDTO));
    }

    @Test
    public void shouldDecryptToken() {
        String token = tokenService.encrypt(userDTO);
        String userEmail = tokenService.decrypt(token);
        assertNotNull(userEmail);
        assertEquals(userEmail, userDTO.getEmail());
    }

    @Test
    public void shouldValidToken() {
        String token = tokenService.encrypt(userDTO);
        assertNotNull(tokenService.decrypt(token));
    }

    @Test
    public void notShouldValidTokenIfTokenIsNull() {
        assertNotNull(tokenService.decrypt(null));
    }

//    @Test
//    public void notShouldValidTokenIfTokenIsInvalid() {
//        String token = "a50c3v9qtokenService3a0Movie@!0ac3";
//        assertNotNull(tokenService.decrypt(token));
//    }
}
