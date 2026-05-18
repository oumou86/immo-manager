package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Agence;
import com.immo.immomanager.service.AgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agences")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AgenceController {

    private final AgenceService agenceService;

    // GET tous les agences
    @GetMapping
    public List<Agence> getAllAgences() {
        return agenceService.getAllAgences();
    }

    // GET une agence par id
    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
        return agenceService.getAgenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST créer une agence
    @PostMapping
    public Agence createAgence(@RequestBody Agence agence) {
        return agenceService.createAgence(agence);
    }

    // PUT modifier une agence
    @PutMapping("/{id}")
    public Agence updateAgence(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.updateAgence(id, agence);
    }

    // DELETE supprimer une agence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        agenceService.deleteAgence(id);
        return ResponseEntity.ok().build();
    }
}