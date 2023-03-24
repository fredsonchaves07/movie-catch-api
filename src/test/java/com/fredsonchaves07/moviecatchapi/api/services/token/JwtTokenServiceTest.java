package com.fredsonchaves07.moviecatchapi.api.services.token;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
        assertNotNull(tokenService.encrypt(Optional.of(userDTO)));
    }

    @Test
    public void shouldDecryptToken() {
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        String userEmail = tokenService.decrypt(Optional.of(tokenDTO));
        assertNotNull(userEmail);
        assertEquals(userEmail, userDTO.getEmail());
    }

    @Test
    public void shouldValidToken() {
        tokenDTO = tokenService.encrypt(Optional.of(userDTO));
        assertNotNull(tokenService.decrypt(Optional.of(tokenDTO)));
    }

    @Test
    public void notShouldValidTokenIfTokenIsNull() {
        assertThrows(
                InvalidTokenException.class,
                () -> tokenService.decrypt(Optional.empty())
        );
    }

    @Test
    public void notShouldValidTokenIfTokenIsInvalid() {
        TokenDTO invalidToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciaiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6eTY2OTQ3MzMyM30." +
                "Bb-HcCS0EYGvczEmZgxjnz-_jo9hVDfecmAMLBuSQzgHefBv-EZCKXkb8F8GEmcgvTw6By0fwiwPz8ooz5mwwg");
        assertThrows(
                InvalidTokenException.class,
                () -> tokenService.decrypt(Optional.of(invalidToken))
        );
    }

    @Test
    public void notShouldValidTokenExpired() {
        TokenDTO expiredToken = new TokenDTO("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9." +
                "eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImV4cCI6MTY2OTk0MjY5NiwiaWF0IjoxNjY5OTQyNjk2fQ." +
                "oC3X-neXhHqxbc1hN09ctA0P2cLG5eMVDdugg50xpjBZg6Fp2oQJdjKc08WYvDjCJzA-1k6XdlqNMx1Vq3rrVw");
        assertThrows(
                ExpiredTokenException.class,
                () -> tokenService.decrypt(Optional.of(expiredToken))
        );
    }

    @Test
    public void notShouldValidTokenIfUserIsNull() {
        assertThrows(
                UserNotFoundException.class,
                () -> tokenService.encrypt(Optional.empty())
        );
    }
}
