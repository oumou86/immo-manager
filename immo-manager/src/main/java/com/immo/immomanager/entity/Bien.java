package com.immo.immomanager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "biens")
public class Bien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double prix;

    private Double surface;
    private Integer nbPieces;
    private Integer nbChambres;

    @Column(nullable = false, length = 100)
    private String ville;

    @Column(length = 255)
    private String adresse;

    private Double latitude;
    private Double longitude;
    private Boolean estLoue = false;
    private LocalDateTime dateDisponibilite;
    private Double loyerEstime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeBien type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutBien statut = StatutBien.DISPONIBLE;

    private LocalDateTime datePublication = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;

    @OneToMany(mappedBy = "bien")
    private List<Image> images;

    @OneToMany(mappedBy = "bien")
    private List<Demande> demandes;

    public enum TypeBien {
        APPARTEMENT, MAISON, TERRAIN, VILLA
    }

    public enum StatutBien {
        DISPONIBLE, LOUE, VENDU
    }
}