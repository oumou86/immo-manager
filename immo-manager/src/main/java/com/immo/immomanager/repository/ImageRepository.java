package com.immo.immomanager.repository;

import com.immo.immomanager.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {
    
    List<Image> findByBienId(Long bienId);
}
