package com.photoalbum.service;

import com.photoalbum.model.Album;
import com.photoalbum.model.Photo;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    public PhotoService(PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
    }

    public Photo save(Photo photo) {
        if (photo.getAlbum() != null && photo.getAlbum().getId() != null) {
            Album album = albumRepository.findById(photo.getAlbum().getId()).orElse(null);
            photo.setAlbum(album);
        }
        return photoRepository.save(photo);
    }

    public List<Photo> getByAlbum(Long albumId) {
        return photoRepository.findByAlbumId(albumId);
    }

    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}
