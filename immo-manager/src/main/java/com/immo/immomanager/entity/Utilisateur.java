package com.immo.immomanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(length = 20)
    private String telephone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Boolean actif = true;

    private LocalDateTime dateCreation = LocalDateTime.now();

    // AJOUT pour les alertes personnalisées
    private Boolean notificationsActivees = true;

    @ManyToOne
    @JoinColumn(name = "agence_id", nullable = true)
    private Agence agence;

    public enum Role {
        SUPER_ADMIN, ADMIN, CLIENT
    }
}