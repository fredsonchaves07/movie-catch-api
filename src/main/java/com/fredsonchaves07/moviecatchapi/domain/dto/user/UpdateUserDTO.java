package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "User update request body")
public record UpdateUserDTO(String name, String password) {
}
