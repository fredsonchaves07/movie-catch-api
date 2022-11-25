package com.fredsonchaves07.moviecatchapi.api.services.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenService implements TokenService {

    private final static int TOKEN_EXPIRATION = 7200000;

    private final static String TOKEN_SECRET = "6728e122-6479-4834-8211-6e1a66838870";

    @Override
    public String encrypt(UserDTO userDTO) {
        return JWT.create()
                .withSubject(userDTO.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(TOKEN_SECRET));
    }

    @Override
    public String decrypt(String token) {
        if (isValid(token)) return null;

    }

    private boolean isValid(String token) {
        try {
            JWT.decode(token).getSubject();
            return true;
        } catch (JWTDecodeException exception) {
            return false;
        }
    }

}
