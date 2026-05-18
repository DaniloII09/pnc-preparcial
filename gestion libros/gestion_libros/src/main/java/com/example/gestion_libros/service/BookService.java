package com.example.gestion_libros.service;

import com.example.gestion_libros.dto.request.BookRequest;
import com.example.gestion_libros.dto.request.BookUpdateRequest;
import com.example.gestion_libros.dto.response.BookResponse;

import java.util.List;

public interface BookService {
    BookResponse registerBook(BookRequest request);
    List<BookResponse> getAllBooks();
    BookResponse getBookById(Long id);
    List<BookResponse> getBooksByAuthor(String author);
    List<BookResponse> getBooksByLanguage(String language);
    List<BookResponse> getBooksByPageRange(Integer minPages, Integer maxPages);
    BookResponse updateBook(Long id, BookUpdateRequest request);
    void deleteBook(Long id);
}
