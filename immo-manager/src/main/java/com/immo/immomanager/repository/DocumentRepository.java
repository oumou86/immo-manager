package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByUtilisateurId(Long utilisateurId);
    List<Document> findByContratId(Long contratId);
    List<Document> findByType(Document.TypeDocument type);
}