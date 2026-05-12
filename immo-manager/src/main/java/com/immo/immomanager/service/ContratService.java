package com.immo.immomanager.service;

import com.immo.immomanager.entity.Contrat;
import com.immo.immomanager.entity.Utilisateur;
import com.immo.immomanager.repository.ContratRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratService {

    private final ContratRepository contratRepository;

    public List<Contrat> getAllContrats() {
        return contratRepository.findAll();
    }

    public Optional<Contrat> getContratById(Long id) {
        return contratRepository.findById(id);
    }

    public List<Contrat> getContratsByClient(Long clientId) {
        return contratRepository.findByClientId(clientId);
    }

    public List<Contrat> getContratsByBien(Long bienId) {
        return contratRepository.findByBienId(bienId);
    }

    public Contrat createContrat(Contrat contrat) {
        // Extraction automatique des infos du client
        Utilisateur client = contrat.getClient();
        contrat.setNomClient(client.getNom());
        contrat.setPrenomClient(client.getPrenom());
        contrat.setEmailClient(client.getEmail());
        contrat.setTelephoneClient(client.getTelephone());
        return contratRepository.save(contrat);
    }

    public Contrat updateContrat(Long id, Contrat contrat) {
        contrat.setId(id);
        return contratRepository.save(contrat);
    }

    public void deleteContrat(Long id) {
        contratRepository.deleteById(id);
    }
}