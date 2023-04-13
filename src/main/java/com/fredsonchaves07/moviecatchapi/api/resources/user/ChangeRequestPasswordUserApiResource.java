package com.fredsonchaves07.moviecatchapi.api.resources.user;

import com.fredsonchaves07.moviecatchapi.api.services.user.ChangeRequestPasswordUserApiService;
import com.fredsonchaves07.moviecatchapi.domain.dto.user.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/change")
public class ChangeRequestPasswordUserApiResource {

    @Autowired
    private ChangeRequestPasswordUserApiService service;

    @GetMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> execute(@RequestBody EmailDTO email) {
        service.execute(email);
        return ResponseEntity.noContent().build();
    }
}
