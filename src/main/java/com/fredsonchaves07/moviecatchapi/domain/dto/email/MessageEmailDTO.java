package com.fredsonchaves07.moviecatchapi.domain.dto.email;

import java.util.HashMap;

public record MessageEmailDTO(String subject, String email, HashMap<String, Object> params) {
}
