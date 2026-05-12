package com.immo.immomanager.service;

import com.immo.immomanager.entity.Agence;
import com.immo.immomanager.entity.Commentaire;
import com.immo.immomanager.repository.AgenceRepository;
import com.immo.immomanager.repository.CommentaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final AgenceRepository agenceRepository; // ← remplace AgenceService

    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    public Optional<Commentaire> getCommentaireById(Long id) {
        return commentaireRepository.findById(id);
    }

    public List<Commentaire> getCommentairesByAgence(Long agenceId) {
        return commentaireRepository.findByAgenceId(agenceId);
    }

    public List<Commentaire> getCommentairesByClient(Long clientId) {
        return commentaireRepository.findByClientId(clientId);
    }

    public List<Commentaire> getCommentairesByStatut(Commentaire.StatutCommentaire statut) {
        return commentaireRepository.findByStatut(statut);
    }

    public List<Commentaire> getCommentairesValidesParAgence(Long agenceId) {
        return commentaireRepository.findByAgenceIdAndStatut(agenceId, Commentaire.StatutCommentaire.VALIDE);
    }

    public Commentaire createCommentaire(Commentaire commentaire) {
        if (commentaire.getNote() < 1 || commentaire.getNote() > 5) {
            throw new RuntimeException("La note doit être entre 1 et 5");
        }
        commentaire.setStatut(Commentaire.StatutCommentaire.EN_ATTENTE);
        commentaire.setDateCreation(LocalDateTime.now());
        return commentaireRepository.save(commentaire);
    }

    public Commentaire updateCommentaire(Long id, Commentaire commentaire) {
        commentaire.setId(id);
        commentaire.setDateMaj(LocalDateTime.now());
        return commentaireRepository.save(commentaire);
    }

    public void deleteCommentaire(Long id) {
        Commentaire commentaire = commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire introuvable : " + id));
        Long agenceId = commentaire.getAgence().getId();
        commentaireRepository.deleteById(id);
        recalculerNoteMoyenne(agenceId); // ← appel local
    }

    public Commentaire validerCommentaire(Long id) {
        Commentaire commentaire = commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire introuvable : " + id));
        commentaire.setStatut(Commentaire.StatutCommentaire.VALIDE);
        commentaire.setDateMaj(LocalDateTime.now());
        Commentaire saved = commentaireRepository.save(commentaire);
        recalculerNoteMoyenne(commentaire.getAgence().getId()); // ← appel local
        return saved;
    }

    public Commentaire declinerCommentaire(Long id) {
        Commentaire commentaire = commentaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commentaire introuvable : " + id));
        commentaire.setStatut(Commentaire.StatutCommentaire.DECLINE);
        commentaire.setDateMaj(LocalDateTime.now());
        return commentaireRepository.save(commentaire);
    }

    public Double getNoteMoyenne(Long agenceId) {
        List<Commentaire> valides = commentaireRepository
                .findByAgenceIdAndStatut(agenceId, Commentaire.StatutCommentaire.VALIDE);
        if (valides.isEmpty()) return 0.0;
        return valides.stream().mapToInt(Commentaire::getNote).average().orElse(0.0);
    }

    // ← recalcul fait ici directement, sans passer par AgenceService
    private void recalculerNoteMoyenne(Long agenceId) {
        List<Commentaire> valides = commentaireRepository
                .findByAgenceIdAndStatut(agenceId, Commentaire.StatutCommentaire.VALIDE);

        double moyenne = valides.stream()
                .mapToInt(Commentaire::getNote)
                .average()
                .orElse(0.0);

        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new RuntimeException("Agence introuvable : " + agenceId));
        agence.setNoteMoyenne(Math.round(moyenne * 10.0) / 10.0);
        agenceRepository.save(agence);
    }
}