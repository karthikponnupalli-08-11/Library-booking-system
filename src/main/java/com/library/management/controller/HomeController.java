
package com.library.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.library.management.dto.BookResponseDTO;
import com.library.management.entity.Book;
import com.library.management.service.BookServicess;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class HomeController {

    private final BookServicess bookServicess;

    public HomeController(BookServicess bookServicess) {
        this.bookServicess = bookServicess;
    }

    // Add book
    @PostMapping("/books")
    public ResponseEntity<Book> saveBook(
            @RequestBody BookResponseDTO dto) {

        Book savedBook = bookServicess.saveBook(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBook);
    }
    // Get all books
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {

        return ResponseEntity.ok(
                bookServicess.getAllBookss());
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookServicess.getBookByIda(id));
    }

 // Replace these mappings in HomeController:

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody Book book) {
        return ResponseEntity.ok(bookServicess.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookServicess.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}