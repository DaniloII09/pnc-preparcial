package com.example.gestion_libros.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {
    @NotBlank(message = "Book title is required")
    @Pattern(regexp = ".*\\D.*", message = "Title cannot contain only numbers")
    private String title;

    @NotBlank(message = "Book author is required")
    private String author;

    @NotBlank(message = "Book ISBN is required")
    @Size(min = 13, max = 13, message = "ISBN must be exactly 13 characters")
    private String isbn;

    @NotNull(message = "Book publication year is required")
    @Min(value = 1900, message = "Book publication year must be 1900 or higher")
    private Integer publicationYear;

    private String language;

    @Min(value = 11, message = "Books pages must be greater than 10")
    @NotNull(message = "Books pages are required")
    private Integer pages;

}
