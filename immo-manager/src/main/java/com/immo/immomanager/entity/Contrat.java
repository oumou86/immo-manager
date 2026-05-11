package com.immo.immomanager.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "contrats")
public class Contrat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroContrat;

    @Column(nullable = false)
    private LocalDate dateDebut;

    @Column(nullable = false)
    private LocalDate dateFin;

    @Column(nullable = false)
    private Double montantLoyer;

    @Column(nullable = false)
    private Double depot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutContrat statut = StatutContrat.ACTIF;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(length = 100)
    private String nomClient;

    @Column(length = 100)
    private String prenomClient;

    @Column(length = 150)
    private String emailClient;

    @Column(length = 20)
    private String telephoneClient;

    @ManyToOne
    @JoinColumn(name = "bien_id", nullable = false)
    private Bien bien;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Utilisateur client;

    public enum StatutContrat {
        ACTIF, EXPIRE, RESILIE
    }
}