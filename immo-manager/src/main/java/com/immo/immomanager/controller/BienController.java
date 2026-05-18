package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Bien;
import com.immo.immomanager.service.BienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/biens")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BienController {

    private final BienService bienService;

    // GET tous les biens
    @GetMapping
    public List<Bien> getAllBiens() {
        return bienService.getAllBiens();
    }

    // GET un bien par id
    @GetMapping("/{id}")
    public ResponseEntity<Bien> getBienById(@PathVariable Long id) {
        return bienService.getBienById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET biens par ville
    @GetMapping("/ville/{ville}")
    public List<Bien> getBiensByVille(@PathVariable String ville) {
        return bienService.getBiensByVille(ville);
    }

    // GET biens par type
    @GetMapping("/type/{type}")
    public List<Bien> getBiensByType(@PathVariable Bien.TypeBien type) {
        return bienService.getBiensByType(type);
    }

    // GET biens par statut
    @GetMapping("/statut/{statut}")
    public List<Bien> getBiensByStatut(@PathVariable Bien.StatutBien statut) {
        return bienService.getBiensByStatut(statut);
    }

    // GET biens par prix
    @GetMapping("/prix")
    public List<Bien> getBiensByPrix(
            @RequestParam Double min,
            @RequestParam Double max) {
        return bienService.getBiensByPrix(min, max);
    }

    // POST créer un bien
    @PostMapping
    public Bien createBien(@RequestBody Bien bien) {
        return bienService.createBien(bien);
    }

    // PUT modifier un bien
    @PutMapping("/{id}")
    public Bien updateBien(@PathVariable Long id, @RequestBody Bien bien) {
        return bienService.updateBien(id, bien);
    }

    // DELETE supprimer un bien
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBien(@PathVariable Long id) {
        bienService.deleteBien(id);
        return ResponseEntity.ok().build();
    }
}