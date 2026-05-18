package com.example.gestion_libros.controller;

import com.example.gestion_libros.dto.request.BookRequest;
import com.example.gestion_libros.dto.request.BookUpdateRequest;
import com.example.gestion_libros.dto.response.BookResponse;
import com.example.gestion_libros.exception.InvalidParameterException;
import com.example.gestion_libros.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> registerBook(@Valid @RequestBody BookRequest request){
        return ResponseEntity.ok(bookService.registerBook(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minPages,
            @RequestParam(required = false) Integer maxPages,
            @RequestParam Map<String, String> allParams
    ){
        Set<String> validParams = Set.of("author", "language", "minPages", "maxPages");

        for (String param : allParams.keySet()) {
            if (!validParams.contains(param)) {
                throw new InvalidParameterException(param + " is not a valid parameter");
            }
        }

        if(StringUtils.hasText(author))
            return ResponseEntity.ok(bookService.getBooksByAuthor(author));
        if(StringUtils.hasText(language))
            return ResponseEntity.ok(bookService.getBooksByLanguage(language));
        if(minPages != null && maxPages != null)
            return ResponseEntity.ok(bookService.getBooksByPageRange(minPages, maxPages));

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id, @Valid @RequestBody BookUpdateRequest request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
