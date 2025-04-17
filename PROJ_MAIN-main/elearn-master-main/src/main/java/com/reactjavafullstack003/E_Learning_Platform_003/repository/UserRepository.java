package com.reactjavafullstack003.E_Learning_Platform_003.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reactjavafullstack003.E_Learning_Platform_003.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find a user by email.
     */
    Optional<User> findByEmail(String email);
}