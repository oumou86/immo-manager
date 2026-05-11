package com.immo.immomanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "demandes")
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeDemande type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDemande statut = StatutDemande.EN_ATTENTE;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private LocalDateTime dateMaj;

    // AJOUT pour les alertes de suivi
    private LocalDateTime dateRelance;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Utilisateur client;

    @ManyToOne
    @JoinColumn(name = "bien_id", nullable = false)
    private Bien bien;

    public enum TypeDemande {
        VISITE, CONTACT, INFO
    }

    public enum StatutDemande {
        EN_ATTENTE, TRAITEE, REFUSEE
    }
}