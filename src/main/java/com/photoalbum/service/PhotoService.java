package com.photoalbum.service;

import com.photoalbum.model.Photo;
import com.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository repository;

    public PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    public List<Photo> getAll() {
        return repository.findAll();
    }

    public Photo getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Photo> getByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public Photo save(Photo photo) {
        return repository.save(photo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
