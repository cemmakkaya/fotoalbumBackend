package com.photoalbum.service;

import com.photoalbum.model.Album;
import com.photoalbum.model.Photo;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository repository;
    private final AlbumRepository albumRepository;

    public PhotoService(PhotoRepository repository, AlbumRepository albumRepository) {
        this.repository = repository;
        this.albumRepository = albumRepository;
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

    public List<Photo> getByAlbum(Long albumId) {
        return repository.findByAlbumId(albumId);
    }

    public Photo save(Photo photo) {
        // 💡 Album korrekt aus der DB holen, bevor gesetzt wird
        if (photo.getAlbum() != null && photo.getAlbum().getId() != null) {
            Album album = albumRepository.findById(photo.getAlbum().getId()).orElse(null);
            photo.setAlbum(album);
        }
        return repository.save(photo);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
