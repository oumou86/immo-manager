package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Contrat;
import com.immo.immomanager.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContratController {

    private final ContratService contratService;

    // GET tous les contrats
    @GetMapping
    public List<Contrat> getAllContrats() {
        return contratService.getAllContrats();
    }

    // GET un contrat par id
    @GetMapping("/{id}")
    public ResponseEntity<Contrat> getContratById(@PathVariable Long id) {
        return contratService.getContratById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET contrats par client
    @GetMapping("/client/{clientId}")
    public List<Contrat> getContratsByClient(@PathVariable Long clientId) {
        return contratService.getContratsByClient(clientId);
    }

    // GET contrats par bien
    @GetMapping("/bien/{bienId}")
    public List<Contrat> getContratsByBien(@PathVariable Long bienId) {
        return contratService.getContratsByBien(bienId);
    }

    // POST créer un contrat
    @PostMapping
    public Contrat createContrat(@RequestBody Contrat contrat) {
        return contratService.createContrat(contrat);
    }

    // PUT modifier un contrat
    @PutMapping("/{id}")
    public Contrat updateContrat(@PathVariable Long id, @RequestBody Contrat contrat) {
        return contratService.updateContrat(id, contrat);
    }

    // DELETE supprimer un contrat
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        contratService.deleteContrat(id);
        return ResponseEntity.ok().build();
    }
}