package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BienRepository extends JpaRepository<Bien, Long> {
    
    List<Bien> findByVille(String ville);
    List<Bien> findByType(Bien.TypeBien type);
    List<Bien> findByStatut(Bien.StatutBien statut);
    List<Bien> findByAgenceId(Long agenceId);
    List<Bien> findByPrixBetween(Double min, Double max);
}