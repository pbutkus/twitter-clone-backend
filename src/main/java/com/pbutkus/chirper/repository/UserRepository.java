package com.pbutkus.chirper.repository;

import com.pbutkus.chirper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);
    Optional<User> findByUsername(String username);
    Optional<User> findBySub(String sub);

}