package com.fredsonchaves07.moviecatchapi.domain.repositories;

import com.fredsonchaves07.moviecatchapi.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query("SELECT r FROM Role r WHERE r.name = 'USER'")
    Role findUserRole();
}
