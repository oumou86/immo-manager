package com.immo.immomanager.service;

import com.immo.immomanager.entity.Agence;
import com.immo.immomanager.repository.AgenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgenceService {

    private final AgenceRepository agenceRepository;

    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    public Optional<Agence> getAgenceById(Long id) {
        return agenceRepository.findById(id);
    }

    public Agence createAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    public Agence updateAgence(Long id, Agence agence) {
        agence.setId(id);
        return agenceRepository.save(agence);
    }

    public void deleteAgence(Long id) {
        agenceRepository.deleteById(id);
    }
}
