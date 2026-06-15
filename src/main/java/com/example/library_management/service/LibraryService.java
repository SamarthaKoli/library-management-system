package com.example.library_management.service;

import com.example.library_management.model.Book;
import com.example.library_management.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * This service class stores and manages books using Spring Data JPA.
 */
@Service
public class LibraryService {

	private final BookRepository bookRepository;

	/**
	 * This constructor injects the book repository.
	 *
	 * @param bookRepository the book repository
	 */
	public LibraryService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	/**
	 * This method adds a new book to the database.
	 *
	 * @param book the book to be added
	 * @return the added book with generated id
	 */
	public Book addBook(Book book) {
		book.setIssued(false);
		return bookRepository.save(book);
	}

	/**
	 * This method returns all books in the database.
	 *
	 * @return the list of books
	 */
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	/**
	 * This method returns the total number of books in the database.
	 *
	 * @return the total book count
	 */
	public long getTotalBooks() {
		return bookRepository.count();
	}

	/**
	 * This method returns the number of available books in the database.
	 *
	 * @return the available book count
	 */
	public long getAvailableBooks() {
		return bookRepository.countByIssuedFalse();
	}

	/**
	 * This method returns the number of issued books in the database.
	 *
	 * @return the issued book count
	 */
	public long getIssuedBooks() {
		return bookRepository.countByIssuedTrue();
	}

	/**
	 * Backward-compatible alias for total books.
	 *
	 * @return the total book count
	 */
	public long getTotalBooksCount() {
		return getTotalBooks();
	}

	/**
	 * Backward-compatible alias for available books.
	 *
	 * @return the available book count
	 */
	public long getAvailableBooksCount() {
		return getAvailableBooks();
	}

	/**
	 * Backward-compatible alias for issued books.
	 *
	 * @return the issued book count
	 */
	public long getIssuedBooksCount() {
		return getIssuedBooks();
	}

	/**
	 * This method finds a book by its id.
	 *
	 * @param id the book id
	 * @return the matching book, or null if not found
	 */
	public Book getBookById(int id) {
		return bookRepository.findById((long) id).orElse(null);
	}

	/**
	 * This method removes a book by id.
	 *
	 * @param id the book id
	 * @return true if the book was removed, otherwise false
	 */
	public boolean removeBookById(int id) {
		Optional<Book> bookOptional = bookRepository.findById((long) id);
		if (bookOptional.isPresent()) {
			bookRepository.delete(bookOptional.get());
			return true;
		}
		return false;
	}

	/**
	 * This method issues a book by id.
	 *
	 * @param id the book id
	 * @return 1 if issued successfully, 0 if not found, -1 if already issued
	 */
	public int issueBookById(int id) {
		Optional<Book> bookOptional = bookRepository.findById((long) id);
		if (bookOptional.isEmpty()) {
			return 0;
		}
		Book book = bookOptional.get();
		if (book.isIssued()) {
			return -1;
		}
		book.setIssued(true);
		bookRepository.save(book);
		return 1;
	}

	/**
	 * This method returns a book by id.
	 *
	 * @param id the book id
	 * @return 1 if returned successfully, 0 if not found, -1 if it was not issued
	 */
	public int returnBookById(int id) {
		Optional<Book> bookOptional = bookRepository.findById((long) id);
		if (bookOptional.isEmpty()) {
			return 0;
		}
		Book book = bookOptional.get();
		if (!book.isIssued()) {
			return -1;
		}
		book.setIssued(false);
		bookRepository.save(book);
		return 1;
	}

	/**
	 * This method searches books by title.
	 *
	 * @param title the title text to search
	 * @return the matching books
	 */
	public List<Book> searchBooksByTitle(String title) {
		String searchText = title == null ? "" : title;
		return bookRepository.findByTitleContainingIgnoreCase(searchText);
	}
}
