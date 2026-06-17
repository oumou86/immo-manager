package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface AgenceRepository extends JpaRepository<Agence, Long> {
    
    Optional<Agence> findByEmail(String email);
    boolean existsByEmail(String email);
}