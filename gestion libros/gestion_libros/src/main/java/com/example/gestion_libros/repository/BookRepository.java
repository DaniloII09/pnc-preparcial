package com.example.gestion_libros.repository;

import com.example.gestion_libros.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    List<Book> findByAuthorIgnoreCase(String author);
    List<Book> findByLanguageIgnoreCase(String language);
    List<Book> findByPagesBetween(Integer minPages, Integer maxPages);
}
