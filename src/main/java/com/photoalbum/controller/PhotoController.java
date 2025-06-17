package com.photoalbum.controller;

import com.photoalbum.model.Photo;
import com.photoalbum.service.PhotoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
@CrossOrigin(origins = "http://localhost:3000")
public class PhotoController {

    private final PhotoService service;

    public PhotoController(PhotoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Photo> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Photo one(@PathVariable Long id) {
        return service.getOne(id);
    }

    @GetMapping("/user/{userId}")
    public List<Photo> byUser(@PathVariable Long userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/album/{albumId}")
    public List<Photo> byAlbum(@PathVariable Long albumId) {
        return service.getByAlbum(albumId);
    }

    @PostMapping
    public Photo create(@RequestBody Photo photo) {
        return service.save(photo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
