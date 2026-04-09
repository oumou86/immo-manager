package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    
    List<Commentaire> findByAgenceId(Long agenceId);
    List<Commentaire> findByClientId(Long clientId);
    List<Commentaire> findByStatut(Commentaire.StatutCommentaire statut);
}
