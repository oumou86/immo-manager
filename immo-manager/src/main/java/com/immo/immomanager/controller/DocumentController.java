package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Document;
import com.immo.immomanager.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentController {

    private final DocumentService documentService;

    // GET tous les documents
    @GetMapping
    public List<Document> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    // GET un document par id
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        return documentService.getDocumentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET documents par utilisateur
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Document> getDocumentsByUtilisateur(@PathVariable Long utilisateurId) {
        return documentService.getDocumentsByUtilisateur(utilisateurId);
    }

    // GET documents par contrat
    @GetMapping("/contrat/{contratId}")
    public List<Document> getDocumentsByContrat(@PathVariable Long contratId) {
        return documentService.getDocumentsByContrat(contratId);
    }

    // GET documents par type
    @GetMapping("/type/{type}")
    public List<Document> getDocumentsByType(@PathVariable Document.TypeDocument type) {
        return documentService.getDocumentsByType(type);
    }

    // POST créer un document
    @PostMapping
    public Document saveDocument(@RequestBody Document document) {
        return documentService.saveDocument(document);
    }

    // DELETE supprimer un document
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.ok().build();
    }
}