package com.pbutkus.chirper.repository;

import com.pbutkus.chirper.entity.Chirp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChirpRepository extends JpaRepository<Chirp, UUID> {
    List<Chirp> findByUserId(UUID id);
}
