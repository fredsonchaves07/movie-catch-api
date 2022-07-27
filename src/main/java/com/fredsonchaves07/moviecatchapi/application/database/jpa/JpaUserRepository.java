package com.fredsonchaves07.moviecatchapi.application.database.jpa;

import com.fredsonchaves07.moviecatchapi.application.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserRepository extends JpaRepository<UserModel, Long> {
}
