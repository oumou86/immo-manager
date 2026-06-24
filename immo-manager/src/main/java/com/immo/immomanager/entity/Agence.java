package com.immo.immomanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "agences")
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(length = 255)
    private String adresse;

    @Column(length = 20)
    private String telephone;

    @Column(unique = true, length = 150)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logo;

    private Double noteMoyenne = 0.0;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "agence")
    @JsonIgnoreProperties({"agence"})
    private List<Utilisateur> utilisateurs;

    @OneToMany(mappedBy = "agence")
    @JsonIgnoreProperties({"agence"})
    private List<Bien> biens;
}