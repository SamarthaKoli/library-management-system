package com.example.library_management.service;

import com.example.library_management.model.Book;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This service class stores and manages all books using in-memory ArrayList storage.
 */
@Service
public class LibraryService {

	private final List<Book> books = new ArrayList<>();
	private int nextId = 1;

	/**
	 * This method adds five sample books when the application starts.
	 */
	@PostConstruct
	public void initializeSampleBooks() {
		addBook(new Book(0, "Java Programming", "Sample Author 1", false));
		addBook(new Book(0, "Python Basics", "Sample Author 2", false));
		addBook(new Book(0, "Data Structures", "Sample Author 3", false));
		addBook(new Book(0, "Spring Boot Guide", "Sample Author 4", false));
		addBook(new Book(0, "Computer Networks", "Sample Author 5", false));
	}

	/**
	 * This method adds a new book to the in-memory list.
	 *
	 * @param book the book to be added
	 * @return the added book with generated id
	 */
	public Book addBook(Book book) {
		Book newBook = new Book();
		newBook.setId(nextId++);
		newBook.setTitle(book.getTitle());
		newBook.setAuthor(book.getAuthor());
		newBook.setIssued(false);
		books.add(newBook);
		return newBook;
	}

	/**
	 * This method returns all books in the library.
	 *
	 * @return the list of books
	 */
	public List<Book> getAllBooks() {
		return books;
	}

	/**
	 * This method finds a book by its id.
	 *
	 * @param id the book id
	 * @return the matching book, or null if not found
	 */
	public Book getBookById(int id) {
		for (Book book : books) {
			if (book.getId() == id) {
				return book;
			}
		}
		return null;
	}

	/**
	 * This method removes a book by id.
	 *
	 * @param id the book id
	 * @return true if the book was removed, otherwise false
	 */
	public boolean removeBookById(int id) {
		Book book = getBookById(id);
		if (book != null) {
			books.remove(book);
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
		Book book = getBookById(id);
		if (book == null) {
			return 0;
		}
		if (book.isIssued()) {
			return -1;
		}
		book.setIssued(true);
		return 1;
	}

	/**
	 * This method returns a book by id.
	 *
	 * @param id the book id
	 * @return 1 if returned successfully, 0 if not found, -1 if it was not issued
	 */
	public int returnBookById(int id) {
		Book book = getBookById(id);
		if (book == null) {
			return 0;
		}
		if (!book.isIssued()) {
			return -1;
		}
		book.setIssued(false);
		return 1;
	}

	/**
	 * This method searches books by title.
	 *
	 * @param title the title text to search
	 * @return the matching books
	 */
	public List<Book> searchBooksByTitle(String title) {
		List<Book> matchedBooks = new ArrayList<>();
		String searchText = title == null ? "" : title.toLowerCase();

		for (Book book : books) {
			if (book.getTitle() != null && book.getTitle().toLowerCase().contains(searchText)) {
				matchedBooks.add(book);
			}
		}

		return matchedBooks;
	}
}
