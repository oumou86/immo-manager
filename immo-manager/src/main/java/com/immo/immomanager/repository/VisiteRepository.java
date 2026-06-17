package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface VisiteRepository extends JpaRepository<Visite, Long> {
    
    List<Visite> findByBienId(Long bienId);
    List<Visite> findByDemandeId(Long demandeId);
    List<Visite> findByStatut(Visite.StatutVisite statut);
}