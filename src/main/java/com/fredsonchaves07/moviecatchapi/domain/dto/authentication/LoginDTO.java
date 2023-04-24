package com.fredsonchaves07.moviecatchapi.domain.dto.authentication;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Login")
public record LoginDTO(
        @ApiModelProperty(required = true) String email, @ApiModelProperty(required = true) String password
) {
}
