package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    // Recherche par agence
    List<Commentaire> findByAgenceId(Long agenceId);

    // Recherche par client
    List<Commentaire> findByClientId(Long clientId);

    // Recherche par statut (EN_ATTENTE, VALIDE, DECLINE)
    List<Commentaire> findByStatut(Commentaire.StatutCommentaire statut);

    // Recherche par agence ET statut (utilisé pour recalculer la note moyenne)
    List<Commentaire> findByAgenceIdAndStatut(Long agenceId, Commentaire.StatutCommentaire statut);
}