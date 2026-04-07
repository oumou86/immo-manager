package com.immo.immomanager.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(length = 255)
    private String nomFichier;

    private Integer ordre = 0;

    private LocalDateTime dateAjout = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "bien_id", nullable = false)
    private Bien bien;

}
