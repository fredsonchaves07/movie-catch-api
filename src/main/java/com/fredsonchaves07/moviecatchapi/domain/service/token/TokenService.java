package com.fredsonchaves07.moviecatchapi.domain.service.token;

import com.fredsonchaves07.moviecatchapi.domain.dto.user.UserDTO;

public interface TokenService {

    public String encrypt(UserDTO userDTO);

    public String decrypt(String token);
}
