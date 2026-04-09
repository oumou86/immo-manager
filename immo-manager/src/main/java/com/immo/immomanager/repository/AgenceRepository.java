package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {
    
    Optional<Agence> findByEmail(String email);
    boolean existsByEmail(String email);
}