package com.photoalbum.controller;

import com.photoalbum.model.Album;
import com.photoalbum.service.AlbumService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = "http://localhost:3000")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping
    public List<Album> all() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Album one(@PathVariable Long id) {
        return service.getOne(id);
    }

    @GetMapping("/user/{userId}")
    public List<Album> byUser(@PathVariable Long userId) {
        return service.getByUser(userId);
    }

    @PostMapping
    public Album create(@RequestBody Album album) {
        return service.save(album);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
