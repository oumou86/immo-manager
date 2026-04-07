package com.immo.immomanager.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "commentaires")
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;

    @Column(nullable = false)
    private Integer note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutCommentaire statut = StatutCommentaire.EN_ATTENTE;

    private LocalDateTime dateCreation = LocalDateTime.now();

    private LocalDateTime dateMaj;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Utilisateur client;

    @ManyToOne
    @JoinColumn(name = "agence_id", nullable = false)
    private Agence agence;

    public enum StatutCommentaire {
        EN_ATTENTE, VALIDE, DECLINE
    }
}


