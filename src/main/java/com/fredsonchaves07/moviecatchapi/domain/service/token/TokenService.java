package com.fredsonchaves07.moviecatchapi.domain.service.token;

import com.fredsonchaves07.moviecatchapi.domain.dto.token.TokenDTO;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;

public interface TokenService {

    TokenDTO encrypt(UserDTO userDTO);

    String decrypt(TokenDTO token);
}
