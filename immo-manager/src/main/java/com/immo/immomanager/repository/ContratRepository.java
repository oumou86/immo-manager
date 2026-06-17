package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface ContratRepository extends JpaRepository<Contrat, Long> {

    Optional<Contrat> findByNumeroContrat(String numeroContrat);
    List<Contrat> findByClientId(Long clientId);
    List<Contrat> findByBienId(Long bienId);
    List<Contrat> findByStatut(Contrat.StatutContrat statut);
}