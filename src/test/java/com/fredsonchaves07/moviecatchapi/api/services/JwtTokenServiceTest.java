package com.fredsonchaves07.moviecatchapi.api.services;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.service.exception.TokenException;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.fredsonchaves07.moviecatchapi.factories.UserFactory.userDTO;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtTokenServiceTest {

    @Autowired
    private TokenService tokenService;

    private UserDTO userDTO;

    private TokenDTO tokenDTO;

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
        tokenDTO = tokenService.encrypt(userDTO);
        String userEmail = tokenService.decrypt(tokenDTO);
        assertNotNull(userEmail);
        assertEquals(userEmail, userDTO.getEmail());
    }

    @Test
    public void shouldValidToken() {
        tokenDTO = tokenService.encrypt(userDTO);
        assertNotNull(tokenService.decrypt(tokenDTO));
    }

    @Test
    public void notShouldValidTokenIfTokenIsNull() {
        assertThrows(
                TokenException.class,
                () -> tokenService.decrypt(null)
        );
    }

    @Test
    public void notShouldValidTokenIfTokenIsInvalid() {
        TokenDTO invalidToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg");
        assertThrows(
                TokenException.class,
                () -> tokenService.decrypt(invalidToken)
        );
    }

    @Test
    public void notShouldValidTokenExpired() {
        TokenDTO expiredToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY2OTQ3NDkwOSwiaWF0IjoxNjY5NDc0OTA5fQ." +
                "bNd5hND3oUbUHLrrBtXZ7w-m6reqyRVyT-TlS92_yoi4zNHH6FdsXppJtQtGhm06LgihSt9PbGjZG4SydpA4mg");
        assertThrows(
                TokenException.class,
                () -> tokenService.decrypt(expiredToken)
        );
    }

    @Test
    public void notShouldValidTokenIfUserIsNull() {
        assertThrows(
                TokenException.class,
                () -> tokenService.encrypt(null)
        );
    }
}
