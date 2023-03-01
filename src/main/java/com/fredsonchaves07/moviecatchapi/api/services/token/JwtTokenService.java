package com.fredsonchaves07.moviecatchapi.api.services.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenService implements TokenService {

    @Value("${jwt.token.expiration}")
    private String tokenExpiration;

    @Value("${jwt.token.secret}")
    private String tokenSecret;

    @Override
    public TokenDTO encrypt(Optional<UserDTO> userDTO) {
        try {
            UserDTO user = userDTO.orElseThrow(UserNotFoundException::new);
            String token = JWT.create()
                    .withSubject(user.getEmail())
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Integer.parseInt(tokenExpiration)))
                    .sign(Algorithm.HMAC512(tokenSecret));
            return new TokenDTO(token);
        } catch (TokenExpiredException exception) {
            throw new ExpiredTokenException();
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public String decrypt(Optional<TokenDTO> tokenDTO) {
        try {
            TokenDTO token = tokenDTO.orElseThrow(InvalidTokenException::new);
            return JWT
                    .require(Algorithm.HMAC512(tokenSecret))
                    .build()
                    .verify(token.token())
                    .getSubject();
        } catch (TokenExpiredException exception) {
            throw new ExpiredTokenException();
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException();
        }
    }
}
