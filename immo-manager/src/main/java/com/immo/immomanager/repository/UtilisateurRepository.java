package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);

    // Recherche par rôle (SUPER_ADMIN, ADMIN, CLIENT)
    List<Utilisateur> findByRole(Utilisateur.Role role);

    // Recherche par agence
    List<Utilisateur> findByAgenceId(Long agenceId);

    // Recherche par statut actif
    List<Utilisateur> findByActif(Boolean actif);
}