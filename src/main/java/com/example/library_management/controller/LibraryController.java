package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.service.LibraryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller class provides REST APIs for library operations.
 */
@RestController
@RequestMapping("/books")
public class LibraryController {

	private final LibraryService libraryService;

	/**
	 * This constructor injects the library service.
	 *
	 * @param libraryService the library service
	 */
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	/**
	 * This method returns all books.
	 *
	 * @return the list of books with HTTP status 200
	 */
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return ResponseEntity.ok(libraryService.getAllBooks());
	}

	/**
	 * This method adds a new book.
	 *
	 * @param book the book details from request body
	 * @return the created book with HTTP status 201
	 */
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		return ResponseEntity.status(HttpStatus.CREATED).body(libraryService.addBook(book));
	}

	/**
	 * This method removes a book by id.
	 *
	 * @param id the book id
	 * @return success or error message with proper HTTP status
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeBook(@PathVariable int id) {
		if (libraryService.removeBookById(id)) {
			return ResponseEntity.ok("Book removed successfully.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
	}

	/**
	 * This method issues a book by id.
	 *
	 * @param id the book id
	 * @return success or error message with proper HTTP status
	 */
	@PutMapping("/{id}/issue")
	public ResponseEntity<String> issueBook(@PathVariable int id) {
		int result = libraryService.issueBookById(id);
		if (result == 1) {
			return ResponseEntity.ok("Book issued successfully.");
		}
		if (result == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is already issued.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
	}

	/**
	 * This method returns a book by id.
	 *
	 * @param id the book id
	 * @return success or error message with proper HTTP status
	 */
	@PutMapping("/{id}/return")
	public ResponseEntity<String> returnBook(@PathVariable int id) {
		int result = libraryService.returnBookById(id);
		if (result == 1) {
			return ResponseEntity.ok("Book returned successfully.");
		}
		if (result == -1) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book is not issued.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");
	}

	/**
	 * This method searches books by title.
	 *
	 * @param title the title text to search
	 * @return the matching books with HTTP status 200
	 */
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
		return ResponseEntity.ok(libraryService.searchBooksByTitle(title));
	}
}
