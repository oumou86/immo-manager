package com.immo.immomanager.controller;

import com.immo.immomanager.entity.Image;
import com.immo.immomanager.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;

    // GET toutes les images
    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    // GET une image par id
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Long id) {
        return imageService.getImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET images par bien
    @GetMapping("/bien/{bienId}")
    public List<Image> getImagesByBien(@PathVariable Long bienId) {
        return imageService.getImagesByBien(bienId);
    }

    // POST ajouter une image
    @PostMapping
    public Image saveImage(@RequestBody Image image) {
        return imageService.saveImage(image);
    }

    // DELETE supprimer une image
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
}