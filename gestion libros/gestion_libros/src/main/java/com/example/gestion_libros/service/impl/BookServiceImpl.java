package com.example.gestion_libros.service.impl;

import com.example.gestion_libros.dto.request.BookRequest;
import com.example.gestion_libros.dto.request.BookUpdateRequest;
import com.example.gestion_libros.dto.response.BookResponse;
import com.example.gestion_libros.exception.BusinessRuleException;
import com.example.gestion_libros.exception.DuplicateEntityException;
import com.example.gestion_libros.exception.EntityNotFoundException;
import com.example.gestion_libros.exception.InvalidParameterException;
import com.example.gestion_libros.model.entity.Book;
import com.example.gestion_libros.repository.BookRepository;
import com.example.gestion_libros.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookResponse registerBook(BookRequest request) {
        if(bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateEntityException("Book", "isbn", request.getIsbn());
        }

        if (request.getPublicationYear() > Year.now().getValue()) {
            throw new BusinessRuleException("Book publication year cannot be greater than current year");
        }
        
        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publicationYear(request.getPublicationYear())
                .language(request.getLanguage())
                .pages(request.getPages())
                .build();

        return toResponse(bookRepository.save(book));
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));
        return toResponse(book);
    }

    @Override
    public List<BookResponse> getBooksByAuthor(String author) {
        return bookRepository
                .findByAuthorIgnoreCase(author)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByLanguage(String language) {
        return bookRepository
                .findByLanguageIgnoreCase(language)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByPageRange(Integer minPages, Integer maxPages) {
        if ((minPages != null && maxPages == null) || (minPages == null && maxPages != null)) {
            throw new InvalidParameterException("Both minPages and maxPages must be provided");
        }

        return bookRepository
                .findByPagesBetween(minPages, maxPages)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));

        if (!request.hasTitle() && !request.hasLanguage()) {
            throw new BusinessRuleException("At least title or language must be provided");
        }

        if (request.hasTitle()) book.setTitle(request.getTitle());
        if (request.hasLanguage()) book.setLanguage(request.getLanguage());

        return toResponse(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book", id));

        bookRepository.delete(book);
    }

    private BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationYear(book.getPublicationYear())
                .language(book.getLanguage())
                .pages(book.getPages())
                .build();
    }
}
