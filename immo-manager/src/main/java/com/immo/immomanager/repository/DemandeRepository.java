package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findByClientId(Long clientId);

    List<Demande> findByBienId(Long bienId);

    List<Demande> findByStatut(Demande.StatutDemande statut);

    // Manquait dans votre repository
    List<Demande> findByType(Demande.TypeDemande type);
}