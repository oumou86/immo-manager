package com.immo.immomanager.service;

import com.immo.immomanager.entity.Demande;
import com.immo.immomanager.repository.DemandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DemandeService {

    private final DemandeRepository demandeRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    public List<Demande> getDemandesByClient(Long clientId) {
        return demandeRepository.findByClientId(clientId);
    }

    public List<Demande> getDemandesByBien(Long bienId) {
        return demandeRepository.findByBienId(bienId);
    }

    public List<Demande> getDemandesByStatut(Demande.StatutDemande statut) {
        return demandeRepository.findByStatut(statut);
    }

    public List<Demande> getDemandesByType(Demande.TypeDemande type) {
        return demandeRepository.findByType(type);
    }

    public Demande createDemande(Demande demande) {
        demande.setStatut(Demande.StatutDemande.EN_ATTENTE);
        demande.setDateCreation(LocalDateTime.now());
        return demandeRepository.save(demande);
    }

    public Demande updateDemande(Long id, Demande demande) {
        demande.setId(id);
        demande.setDateMaj(LocalDateTime.now());
        return demandeRepository.save(demande);
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    // Changer le statut d'une demande
    public Demande changerStatut(Long id, Demande.StatutDemande nouveauStatut) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));
        demande.setStatut(nouveauStatut);
        demande.setDateMaj(LocalDateTime.now());
        return demandeRepository.save(demande);
    }

    // Générer une alerte de relance si la demande est en attente depuis trop longtemps
    public String genererAlerteRelance(Long id) {
        Demande demande = demandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));

        if (demande.getStatut() != Demande.StatutDemande.EN_ATTENTE) {
            return "✅ Demande déjà traitée";
        }

        LocalDateTime limite = LocalDateTime.now().minusDays(3);
        if (demande.getDateCreation().isBefore(limite)) {
            demande.setDateRelance(LocalDateTime.now());
            demandeRepository.save(demande);
            return "⚠️ Relance générée : demande en attente depuis plus de 3 jours";
        }

        return "✅ Aucune relance nécessaire";
    }

    // Statistiques des demandes pour un bien
    public String getStatistiquesBien(Long bienId) {
        List<Demande> demandes = demandeRepository.findByBienId(bienId);
        long enAttente = demandes.stream().filter(d -> d.getStatut() == Demande.StatutDemande.EN_ATTENTE).count();
        long traitees  = demandes.stream().filter(d -> d.getStatut() == Demande.StatutDemande.TRAITEE).count();
        long refusees  = demandes.stream().filter(d -> d.getStatut() == Demande.StatutDemande.REFUSEE).count();

        return String.format(
                "📊 Bien #%d | Total: %d | En attente: %d | Traitées: %d | Refusées: %d",
                bienId, demandes.size(), enAttente, traitees, refusees
        );
    }
}