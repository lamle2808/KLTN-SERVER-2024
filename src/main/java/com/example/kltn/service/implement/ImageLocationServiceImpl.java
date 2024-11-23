package com.example.kltn.service.implement;

import com.example.kltn.entity.ImageLocation;
import com.example.kltn.repository.ImageLocationRepo;
import com.example.kltn.service.ImageLocationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageLocationServiceImpl implements ImageLocationService {
    
    private final ImageLocationRepo imageLocationRepo;
    
    @Override
    public ImageLocation save(ImageLocation imageLocation) {
        return imageLocationRepo.save(imageLocation);
    }
    
    @Override
    public void delete(Long id) {
        imageLocationRepo.deleteById(id);
    }
    
    @Override
    public ImageLocation getById(Long id) {
        return imageLocationRepo.findById(id).orElse(null);
    }
} 