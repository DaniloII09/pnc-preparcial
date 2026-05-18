package com.example.gestion_libros.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, updatable = false, length = 150)
    private String author;

    @Column(nullable = false, updatable = false, unique = true, length = 13)
    private String isbn;

    @Column(nullable = false, updatable = false)
    private Integer publicationYear;

    @Column(length = 50)
    private String language;

    @Column(nullable = false, updatable = false)
    private Integer pages;
}
