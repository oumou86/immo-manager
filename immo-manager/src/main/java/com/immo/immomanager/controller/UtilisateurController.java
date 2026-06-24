package com.immo.immomanager.controller;

import com.immo.immomanager.dto.RegisterRequest;
import com.immo.immomanager.entity.Utilisateur;
import com.immo.immomanager.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final PasswordEncoder passwordEncoder;

    // ============================================================
    // ROUTES SPÉCIFIQUES EN PREMIER (avant /{id})
    // ============================================================

    @GetMapping("/tous")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<Utilisateur>> getTousLesUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getStats() {
        long totalClients = utilisateurService.getAllUtilisateurs().stream()
                .filter(u -> u.getRole() == Utilisateur.Role.CLIENT)
                .count();
        long totalAdmins = utilisateurService.getAllUtilisateurs().stream()
                .filter(u -> u.getRole() == Utilisateur.Role.ADMIN)
                .count();
        return ResponseEntity.ok(Map.of(
                "totalClients", totalClients,
                "totalAdmins", totalAdmins
        ));
    }

    @PostMapping("/creer-admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> creerAdmin(@Valid @RequestBody RegisterRequest request) {
        if (utilisateurService.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Un compte existe déjà avec cet email"));
        }

        Utilisateur admin = new Utilisateur();
        admin.setNom(request.getNom());
        admin.setPrenom(request.getPrenom());
        admin.setEmail(request.getEmail());
        admin.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        admin.setTelephone(request.getTelephone());
        admin.setRole(Utilisateur.Role.ADMIN);

        Utilisateur saved = utilisateurService.createUtilisateur(admin);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Compte Admin créé avec succès",
                "id", saved.getId(),
                "email", saved.getEmail(),
                "role", saved.getRole().name()
        ));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Utilisateur> getUtilisateurByEmail(@PathVariable String email) {
        return utilisateurService.getUtilisateurByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================================
    // ROUTES GÉNÉRIQUES AVEC /{id} EN DERNIER
    // ============================================================

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    public Utilisateur updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.updateUtilisateur(id, utilisateur);
    }

    @PutMapping("/{id}/toggle-actif")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> toggleActif(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id)
                .map(utilisateur -> {
                    utilisateur.setActif(!utilisateur.getActif());
                    utilisateurService.updateUtilisateur(id, utilisateur);
                    String statut = utilisateur.getActif() ? "activé" : "désactivé";
                    return ResponseEntity.ok(Map.of(
                            "message", "Compte " + statut + " avec succès",
                            "actif", utilisateur.getActif()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.ok().build();
    }
}