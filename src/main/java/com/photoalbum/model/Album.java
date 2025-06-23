package com.photoalbum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private int year;

    @Column(length = 85)
    private String description;

    private String imageUrl; // ✅ <-- hier ist dein Bildpfad

    private Long userId;
}
