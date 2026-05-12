package com.immo.immomanager.service;

import com.immo.immomanager.entity.Document;
import com.immo.immomanager.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;

    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    public Optional<Document> getDocumentById(Long id) {
        return documentRepository.findById(id);
    }

    public List<Document> getDocumentsByUtilisateur(Long utilisateurId) {
        return documentRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Document> getDocumentsByContrat(Long contratId) {
        return documentRepository.findByContratId(contratId);
    }

    public List<Document> getDocumentsByType(Document.TypeDocument type) {
        return documentRepository.findByType(type);
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
