package com.immo.immomanager.service;

import com.immo.immomanager.entity.Visite;
import com.immo.immomanager.repository.VisiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public List<Visite> getAllVisites() {
        return visiteRepository.findAll();
    }

    public Optional<Visite> getVisiteById(Long id) {
        return visiteRepository.findById(id);
    }

    public List<Visite> getVisitesByBien(Long bienId) {
        return visiteRepository.findByBienId(bienId);
    }

    public List<Visite> getVisitesByDemande(Long demandeId) {
        return visiteRepository.findByDemandeId(demandeId);
    }

    public List<Visite> getVisitesByStatut(Visite.StatutVisite statut) {
        return visiteRepository.findByStatut(statut);
    }

    public Visite createVisite(Visite visite) {
        visite.setStatut(Visite.StatutVisite.PLANIFIEE);
        visite.setDateCreation(LocalDateTime.now());

        // Rappel automatique 24h avant la visite
        if (visite.getDateVisite() != null) {
            visite.setDateRappel(visite.getDateVisite().minusHours(24));
        }

        return visiteRepository.save(visite);
    }

    public Visite updateVisite(Long id, Visite visite) {
        visite.setId(id);
        // Recalculer le rappel si la date de visite change
        if (visite.getDateVisite() != null) {
            visite.setDateRappel(visite.getDateVisite().minusHours(24));
        }
        return visiteRepository.save(visite);
    }

    public void deleteVisite(Long id) {
        visiteRepository.deleteById(id);
    }

    // Changer le statut d'une visite
    public Visite changerStatut(Long id, Visite.StatutVisite nouveauStatut) {
        Visite visite = visiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visite introuvable : " + id));
        visite.setStatut(nouveauStatut);
        return visiteRepository.save(visite);
    }

    // Confirmer une visite
    public Visite confirmerVisite(Long id) {
        return changerStatut(id, Visite.StatutVisite.CONFIRMEE);
    }

    // Annuler une visite
    public Visite annulerVisite(Long id) {
        return changerStatut(id, Visite.StatutVisite.ANNULEE);
    }

    // Marquer une visite comme effectuée
    public Visite marquerEffectuee(Long id, String notes) {
        Visite visite = visiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visite introuvable : " + id));
        visite.setStatut(Visite.StatutVisite.EFFECTUEE);
        if (notes != null && !notes.isBlank()) {
            visite.setNotes(notes);
        }
        return visiteRepository.save(visite);
    }

    // Vérifier les rappels à envoyer (visites dans les prochaines 24h)
    public List<Visite> getVisitesAvecRappelProchain() {
        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime dans24h = maintenant.plusHours(24);
        return visiteRepository.findAll().stream()
                .filter(v -> v.getStatut() == Visite.StatutVisite.PLANIFIEE || v.getStatut() == Visite.StatutVisite.CONFIRMEE)
                .filter(v -> v.getDateVisite() != null)
                .filter(v -> v.getDateVisite().isAfter(maintenant) && v.getDateVisite().isBefore(dans24h))
                .toList();
    }

    // Statistiques des visites pour un bien
    public String getStatistiquesBien(Long bienId) {
        List<Visite> visites = visiteRepository.findByBienId(bienId);
        long planifiees  = visites.stream().filter(v -> v.getStatut() == Visite.StatutVisite.PLANIFIEE).count();
        long confirmees  = visites.stream().filter(v -> v.getStatut() == Visite.StatutVisite.CONFIRMEE).count();
        long effectuees  = visites.stream().filter(v -> v.getStatut() == Visite.StatutVisite.EFFECTUEE).count();
        long annulees    = visites.stream().filter(v -> v.getStatut() == Visite.StatutVisite.ANNULEE).count();

        return String.format(
                "📊 Bien #%d | Total: %d | Planifiées: %d | Confirmées: %d | Effectuées: %d | Annulées: %d",
                bienId, visites.size(), planifiees, confirmees, effectuees, annulees
        );
    }
}