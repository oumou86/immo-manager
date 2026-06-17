package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUtilisateurId(Long utilisateurId);
    List<Document> findByContratId(Long contratId);
    List<Document> findByType(Document.TypeDocument type);
}