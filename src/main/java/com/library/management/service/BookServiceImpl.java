
package com.library.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.management.dto.BookMapper;
import com.library.management.dto.BookResponseDTO;
import com.library.management.entity.Book;
import com.library.management.entity.Category;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.CategoryRepository;

@Service
public class BookServiceImpl implements BookServicess {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookServiceImpl(
            BookRepository bookRepository,
            CategoryRepository categoryRepository) {

        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    // Save Book with Category
    @Override
    public Book saveBook(BookResponseDTO dto) {

        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(() ->
                    new RuntimeException(
                        "Category not found with ID: "
                        + dto.getCategoryId()
                    )
                );

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setQuantity(dto.getQuantity());

        book.setCategory(category);

        return bookRepository.save(book);
    }

    // Get All Books
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get Book By ID
    @Override
    public Book getBookById(Long id) {

        return bookRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Book is not Available"));
    }

    // Update Book
    @Override
    public Book updateBook(Long id, Book book) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Book is not Available"));

        Long categoryId = book.getCategory().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                    new RuntimeException("Category is not Available"));

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setQuantity(book.getQuantity());
        existingBook.setCategory(category);

        return bookRepository.save(existingBook);
    }

    // Delete Book
    @Override
    public void deleteBook(Long id) {

        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book is not Available");
        }

        bookRepository.deleteById(id);
    }
    
    @Override
    public BookResponseDTO getBookByIda(Long id) {

        Book book = bookRepository.findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Book not found"));

        return BookMapper.toDTO(book);
    }
    
    @Override
    public List<BookResponseDTO> getAllBookss() {

        return bookRepository.findAll()
            .stream()
            .map(BookMapper::toDTO)
            .toList();
    }

	@Override
	public Book saveBook(Book book) {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}
