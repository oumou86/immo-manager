package com.immo.immomanager.service;

import com.immo.immomanager.entity.Image;
import com.immo.immomanager.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    public List<Image> getImagesByBien(Long bienId) {
        return imageRepository.findByBienId(bienId).stream()
                .sorted(Comparator.comparingInt(Image::getOrdre))
                .toList();
    }

    public Image createImage(Image image) {
        image.setDateAjout(LocalDateTime.now());
        if (image.getOrdre() == null) {
            // Attribuer automatiquement le prochain ordre
            List<Image> existantes = imageRepository.findByBienId(image.getBien().getId());
            image.setOrdre(existantes.size());
        }
        return imageRepository.save(image);
    }

    public Image updateImage(Long id, Image image) {
        image.setId(id);
        return imageRepository.save(image);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    // Réorganiser l'ordre des images d'un bien
    public List<Image> reordonnerImages(Long bienId, List<Long> ordreIds) {
        List<Image> images = imageRepository.findByBienId(bienId);
        for (int i = 0; i < ordreIds.size(); i++) {
            final int index = i;
            images.stream()
                    .filter(img -> img.getId().equals(ordreIds.get(index)))
                    .findFirst()
                    .ifPresent(img -> {
                        img.setOrdre(index);
                        imageRepository.save(img);
                    });
        }
        return getImagesByBien(bienId);
    }

    // Récupérer la première image (photo principale) d'un bien
    public Optional<Image> getImagePrincipale(Long bienId) {
        return imageRepository.findByBienId(bienId).stream()
                .min(Comparator.comparingInt(Image::getOrdre));
    }

    // Supprimer toutes les images d'un bien
    public void deleteAllImagesByBien(Long bienId) {
        List<Image> images = imageRepository.findByBienId(bienId);
        imageRepository.deleteAll(images);
    }
}