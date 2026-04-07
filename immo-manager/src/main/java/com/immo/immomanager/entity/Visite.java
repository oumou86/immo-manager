package com.immo.immomanager.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "visites")
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateVisite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutVisite statut = StatutVisite.PLANIFIEE;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime dateCreation = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "bien_id", nullable = false)
    private Bien bien;

    public enum StatutVisite {
        PLANIFIEE, CONFIRMEE, ANNULEE, EFFECTUEE
    }
}
