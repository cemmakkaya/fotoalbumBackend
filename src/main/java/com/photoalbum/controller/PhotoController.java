package com.photoalbum.controller;

import com.photoalbum.model.Album;
import com.photoalbum.model.Photo;
import com.photoalbum.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("/upload")
    public Photo uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @RequestParam("albumId") Long albumId,
            @RequestParam("userId") Long userId
    ) throws IOException {
        String imageUrl = saveImageToDisk(file);

        Photo photo = new Photo();
        photo.setUrl(imageUrl);
        photo.setUserId(userId);

        Album album = new Album();
        album.setId(albumId);
        photo.setAlbum(album);

        return photoService.save(photo);
    }

    @GetMapping("/album/{albumId}")
    public List<Photo> byAlbum(@PathVariable Long albumId) {
        return photoService.getByAlbum(albumId);
    }

    private String saveImageToDisk(MultipartFile file) throws IOException {
        String folder = "uploads/";
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(folder + filename);
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());
        return "http://localhost:7070/uploads/" + filename;
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable Long id) {
        photoService.delete(id);
    }

}
