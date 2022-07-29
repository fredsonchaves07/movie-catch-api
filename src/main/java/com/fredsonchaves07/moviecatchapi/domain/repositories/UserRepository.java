package com.fredsonchaves07.moviecatchapi.domain.repositories;

import com.fredsonchaves07.moviecatchapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
