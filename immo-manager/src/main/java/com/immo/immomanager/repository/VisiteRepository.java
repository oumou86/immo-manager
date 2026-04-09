package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VisiteRepository extends JpaRepository<Visite, Long> {
    
    List<Visite> findByBienId(Long bienId);
    List<Visite> findByDemandeId(Long demandeId);
    List<Visite> findByStatut(Visite.StatutVisite statut);
}