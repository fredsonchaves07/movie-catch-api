package com.fredsonchaves07.moviecatchapi.api.services.token;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;
import com.fredsonchaves07.moviecatchapi.domain.service.token.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
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
            String token = Jwts
                    .builder()
                    .setClaims(new HashMap<>())
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + Integer.parseInt(tokenExpiration)))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return new TokenDTO(token);
        } catch (MalformedJwtException | IllegalArgumentException exception) {
            throw new InvalidTokenException();
        }
    }

    @Override
    public String decrypt(Optional<TokenDTO> tokenDTO) {
        try {
            TokenDTO token = tokenDTO.orElseThrow(InvalidTokenException::new);
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token.getToken())
                    .getBody()
                    .getSubject();
        } catch (WeakKeyException exception) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException | IllegalArgumentException exception) {
            throw new InvalidTokenException();
        }
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(tokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
