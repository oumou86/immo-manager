package com.immo.immomanager.service;

import com.immo.immomanager.entity.Bien;
import com.immo.immomanager.repository.BienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;  // ← CET IMPORT MANquait
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BienService {

    private final BienRepository bienRepository;

    public List<Bien> getAllBiens() {
        return bienRepository.findAll();
    }

    public Optional<Bien> getBienById(Long id) {
        return bienRepository.findById(id);
    }

    public List<Bien> getBiensByVille(String ville) {
        return bienRepository.findByVille(ville);
    }

    public List<Bien> getBiensByType(Bien.TypeBien type) {
        return bienRepository.findByType(type);
    }

    public List<Bien> getBiensByStatut(Bien.StatutBien statut) {
        return bienRepository.findByStatut(statut);
    }

    public List<Bien> getBiensByPrix(Double min, Double max) {
        return bienRepository.findByPrixBetween(min, max);
    }

    // NOUVEAU : Simulateur de loyer Bamako
    public Double simulerLoyer(Bien bien) {
        if (bien.getSurface() == null) return 0.0;
        
        double prixParM2 = 0;
        String ville = bien.getVille() != null ? bien.getVille().toLowerCase() : "";
        
        // Tarifs Bamako par quartier (FCFA/m²)
        if (ville.contains("aci") || ville.contains("badalabougou")) {
            prixParM2 = 2500; // Quartiers chics
        } else if (ville.contains("sotuba") || ville.contains("djicoroni")) {
            prixParM2 = 1800;
        } else {
            prixParM2 = 1500; // Autres quartiers
        }
        
        // Ajustements
        if (bien.getNbChambres() != null && bien.getNbChambres() > 2) {
            prixParM2 *= 1.2;
        }
        if (bien.getType() == Bien.TypeBien.VILLA) {
            prixParM2 *= 1.5;
        }
        
        return bien.getSurface() * prixParM2;
    }

    // NOUVEAU : Recherche par rayon géographique (Bamako)
    public List<Bien> getBiensProches(Double lat, Double lng, Double rayonKm) {
        return bienRepository.findAll().stream()
            .filter(b -> b.getLatitude() != null && b.getLongitude() != null)
            .filter(b -> distanceEnKm(lat, lng, b.getLatitude(), b.getLongitude()) <= rayonKm)
            .toList();
    }

    private double distanceEnKm(Double lat1, Double lng1, Double lat2, Double lng2) {
        double earthRadius = 6371; // Rayon Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return earthRadius * c;
    }

    // NOUVEAU : Génération d'alertes
    public String genererAlerte(Bien bien) {
        StringBuilder alertes = new StringBuilder();
        
        if (bien.getEstLoue() != null && !bien.getEstLoue() && 
            bien.getDatePublication().isBefore(LocalDateTime.now().minusDays(30))) {
            alertes.append("⚠️ Bien inactif depuis 30 jours\n");
        }
        
        if (bien.getDateDisponibilite() != null && 
            bien.getDateDisponibilite().isBefore(LocalDateTime.now().plusDays(7))) {
            alertes.append("📅 Disponible bientôt\n");
        }
        
        return alertes.length() > 0 ? alertes.toString() : "✅ Aucun problème";
    }

    // NOUVEAU : Mise à jour avec simulateur auto
    public Bien createBien(Bien bien) {
        if (bien.getLoyerEstime() == null) {
            bien.setLoyerEstime(simulerLoyer(bien));
        }
        return bienRepository.save(bien);
    }

    public Bien updateBien(Long id, Bien bien) {
        bien.setId(id);
        if (bien.getLoyerEstime() == null || bien.getSurface() != null) {
            bien.setLoyerEstime(simulerLoyer(bien));
        }
        return bienRepository.save(bien);
    }

    public void deleteBien(Long id) {
        bienRepository.deleteById(id);
    }
}