package com.immo.immomanager.service;

import com.immo.immomanager.entity.Utilisateur;
import com.immo.immomanager.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public List<Utilisateur> getUtilisateursByRole(Utilisateur.Role role) {
        return utilisateurRepository.findByRole(role);
    }

    public List<Utilisateur> getUtilisateursByAgence(Long agenceId) {
        return utilisateurRepository.findByAgenceId(agenceId);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé : " + utilisateur.getEmail());
        }
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtilisateur(Long id, Utilisateur utilisateur) {
        utilisateur.setId(id);
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // Activer / désactiver un compte
    public Utilisateur toggleActif(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));
        utilisateur.setActif(!Boolean.TRUE.equals(utilisateur.getActif()));
        return utilisateurRepository.save(utilisateur);
    }

    // Activer / désactiver les notifications
    public Utilisateur toggleNotifications(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable : " + id));
        utilisateur.setNotificationsActivees(!Boolean.TRUE.equals(utilisateur.getNotificationsActivees()));
        return utilisateurRepository.save(utilisateur);
    }

    // Vérifier si un utilisateur est actif
    public boolean estActif(Long id) {
        return utilisateurRepository.findById(id)
                .map(u -> Boolean.TRUE.equals(u.getActif()))
                .orElse(false);
    }
}