package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Commentaire;
import com.immo.immomanager.service.CommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/commentaires")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CommentaireController {

    private final CommentaireService commentaireService;

    // GET tous les commentaires
    @GetMapping
    public List<Commentaire> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    // GET un commentaire par id
    @GetMapping("/{id}")
    public ResponseEntity<Commentaire> getCommentaireById(@PathVariable Long id) {
        return commentaireService.getCommentaireById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET commentaires par agence
    @GetMapping("/agence/{agenceId}")
    public List<Commentaire> getCommentairesByAgence(@PathVariable Long agenceId) {
        return commentaireService.getCommentairesByAgence(agenceId);
    }

    // GET commentaires par statut
    @GetMapping("/statut/{statut}")
    public List<Commentaire> getCommentairesByStatut(@PathVariable Commentaire.StatutCommentaire statut) {
        return commentaireService.getCommentairesByStatut(statut);
    }

    // POST créer un commentaire
    @PostMapping
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.createCommentaire(commentaire);
    }

    // PUT modifier un commentaire
    @PutMapping("/{id}")
    public Commentaire updateCommentaire(@PathVariable Long id, @RequestBody Commentaire commentaire) {
        return commentaireService.updateCommentaire(id, commentaire);
    }

    // DELETE supprimer un commentaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentaire(@PathVariable Long id) {
        commentaireService.deleteCommentaire(id);
        return ResponseEntity.ok().build();
    }
}