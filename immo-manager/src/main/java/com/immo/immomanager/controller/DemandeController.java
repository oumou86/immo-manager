package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Demande;
import com.immo.immomanager.service.DemandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DemandeController {

    private final DemandeService demandeService;

    // GET toutes les demandes
    @GetMapping
    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemandes();
    }

    // GET une demande par id
    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        return demandeService.getDemandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET demandes par client
    @GetMapping("/client/{clientId}")
    public List<Demande> getDemandesByClient(@PathVariable Long clientId) {
        return demandeService.getDemandesByClient(clientId);
    }

    // GET demandes par bien
    @GetMapping("/bien/{bienId}")
    public List<Demande> getDemandesByBien(@PathVariable Long bienId) {
        return demandeService.getDemandesByBien(bienId);
    }

    // POST créer une demande
    @PostMapping
    public Demande createDemande(@RequestBody Demande demande) {
        return demandeService.createDemande(demande);
    }

    // PUT modifier une demande
    @PutMapping("/{id}")
    public Demande updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        return demandeService.updateDemande(id, demande);
    }

    // DELETE supprimer une demande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.ok().build();
    }
}