package com.photoalbum.service;

import com.photoalbum.model.Album;
import com.photoalbum.repository.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository repository;

    public AlbumService(AlbumRepository repository) {
        this.repository = repository;
    }

    public List<Album> getAll() {
        return repository.findAll();
    }

    public Album getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Album> getByUser(Long userId) {
        return repository.findByUserId(String.valueOf(userId));
    }

    public Album save(Album album) {
        return repository.save(album);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
