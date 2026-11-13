
package com.library.management.dto;

import com.library.management.entity.Book;
import com.library.management.entity.Category;

public class BookMapper {

    public static BookResponseDTO toDTO(Book book) {

        Category category = book.getCategory();

        return new BookResponseDTO(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getQuantity(),
            category != null ? category.getId() : null,
            category != null ? category.getCategoryName() : null
        );
    }
}
