package com.photoalbum.repository;

import com.photoalbum.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByUserId(String userId);

}
