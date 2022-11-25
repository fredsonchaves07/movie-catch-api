package com.fredsonchaves07.moviecatchapi.api.services.user;

import com.fredsonchaves07.moviecatchapi.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ConfirmUserServiceTest {

    @Autowired
    private UserRepository userRepository;
}
