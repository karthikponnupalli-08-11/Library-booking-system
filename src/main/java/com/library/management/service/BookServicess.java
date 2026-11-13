package com.library.management.service;

	import java.util.List;

import com.library.management.dto.BookResponseDTO;
import com.library.management.entity.Book;

	public interface BookServicess {

	    // Save a new book
	    Book saveBook(Book book);

	    // Get all books
	    List<Book> getAllBooks();

	    // Get book by ID
	    Book getBookById(Long id);

	    // Update book
	    Book updateBook(Long id, Book book);

	    // Delete book
	    void deleteBook(Long id);
	    
	    BookResponseDTO getBookByIda(Long id);

	    List<BookResponseDTO> getAllBookss();

		Book saveBook(BookResponseDTO dto);
	

}
