package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Visite;
import com.immo.immomanager.service.VisiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visites")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VisiteController {

    private final VisiteService visiteService;

    // GET toutes les visites
    @GetMapping
    public List<Visite> getAllVisites() {
        return visiteService.getAllVisites();
    }

    // GET une visite par id
    @GetMapping("/{id}")
    public ResponseEntity<Visite> getVisiteById(@PathVariable Long id) {
        return visiteService.getVisiteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET visites par bien
    @GetMapping("/bien/{bienId}")
    public List<Visite> getVisitesByBien(@PathVariable Long bienId) {
        return visiteService.getVisitesByBien(bienId);
    }

    // GET visites par statut
    @GetMapping("/statut/{statut}")
    public List<Visite> getVisitesByStatut(@PathVariable Visite.StatutVisite statut) {
        return visiteService.getVisitesByStatut(statut);
    }

    // POST créer une visite
    @PostMapping
    public Visite createVisite(@RequestBody Visite visite) {
        return visiteService.createVisite(visite);
    }

    // PUT modifier une visite
    @PutMapping("/{id}")
    public Visite updateVisite(@PathVariable Long id, @RequestBody Visite visite) {
        return visiteService.updateVisite(id, visite);
    }

    // DELETE supprimer une visite
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVisite(@PathVariable Long id) {
        visiteService.deleteVisite(id);
        return ResponseEntity.ok().build();
    }
}