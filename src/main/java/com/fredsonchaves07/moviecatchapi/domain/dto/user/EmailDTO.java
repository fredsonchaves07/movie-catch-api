package com.fredsonchaves07.moviecatchapi.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Email")
public record EmailDTO(@ApiModelProperty(required = true) String email) {

    @Override
    public String toString() {
        return email;
    }
}
