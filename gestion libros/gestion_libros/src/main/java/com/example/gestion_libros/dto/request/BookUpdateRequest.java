package com.example.gestion_libros.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookUpdateRequest {
    @Pattern(regexp = ".*\\D.*", message = "Title cannot contain only numbers")
    private String title;

    private String language;

    public boolean hasTitle() {
        return StringUtils.hasText(this.title);
    }

    public boolean hasLanguage() {
        return StringUtils.hasText(this.language);
    }
}
