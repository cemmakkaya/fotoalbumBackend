package com.photoalbum.controller;

import com.photoalbum.model.Album;
import com.photoalbum.model.Photo;
import com.photoalbum.service.AlbumService;
import com.photoalbum.repository.AlbumRepository;
import com.photoalbum.repository.PhotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = "http://localhost:4200")
public class AlbumController {

    private final AlbumService service;
    private final AlbumRepository albumRepository;
    private final PhotoRepository photoRepository;

    public AlbumController(AlbumService service, AlbumRepository albumRepository, PhotoRepository photoRepository) {
        this.service = service;
        this.albumRepository = albumRepository;
        this.photoRepository = photoRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Album> getAlbumsByUser(@PathVariable String userId) {
        return albumRepository.findByUserId(Long.valueOf(userId));
    }


    @PostMapping("/upload")
    public Album uploadAlbum(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titel") String titel,
            @RequestParam("jahr") int jahr,
            @RequestParam("beschreibung") String beschreibung,
            @RequestParam("userId") Long userId
    ) throws IOException {
        String imageUrl = saveImageToDisk(file);

        Album album = new Album();
        album.setTitle(titel);
        album.setYear(jahr);
        album.setDescription(beschreibung);
        album.setUserId(userId);
        album.setImageUrl(imageUrl);

        return service.save(album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        if (!albumRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Lösche alle verknüpften Fotos vom Album
        List<Photo> photos = photoRepository.findByAlbumId(id);
        for (Photo photo : photos) {
            if (photo.getUrl() != null) {
                try {
                    String pathStr = photo.getUrl().replace("http://localhost:7070/", "");
                    File file = new File(pathStr);
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    System.err.println("Fehler beim Löschen von Bilddatei: " + e.getMessage());
                }
            }
            photoRepository.delete(photo);
        }

        albumRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String saveImageToDisk(MultipartFile file) throws IOException {
        String folder = "uploads/";
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(folder + filename);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return "http://localhost:7070/uploads/" + filename;
    }
}
