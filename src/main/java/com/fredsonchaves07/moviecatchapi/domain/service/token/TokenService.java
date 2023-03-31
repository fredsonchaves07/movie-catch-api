package com.fredsonchaves07.moviecatchapi.domain.service.token;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.ExpiredTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.InvalidTokenException;
import com.fredsonchaves07.moviecatchapi.domain.exceptions.UserNotFoundException;

import java.util.Optional;

public interface TokenService {

    TokenDTO encrypt(Optional<UserDTO> userDTO) throws UserNotFoundException;

    String decrypt(Optional<TokenDTO> token) throws ExpiredTokenException, InvalidTokenException;
}
