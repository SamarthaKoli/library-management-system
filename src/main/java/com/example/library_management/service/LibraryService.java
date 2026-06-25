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

import com.example.library_management.model.IssueRecord;
import com.example.library_management.model.User;
import com.example.library_management.repository.IssueRecordRepository;
import com.example.library_management.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

	private final BookRepository bookRepository;
	private final IssueRecordRepository issueRecordRepository;
	private final UserRepository userRepository;

	public LibraryService(BookRepository bookRepository, IssueRecordRepository issueRecordRepository, UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.issueRecordRepository = issueRecordRepository;
		this.userRepository = userRepository;
	}

	public Book addBook(Book book) {
		book.setAvailable(true);
		if (book.getCreatedAt() == null) {
			book.setCreatedAt(LocalDateTime.now());
		}
		return bookRepository.save(book);
	}

	public Book updateBook(Book book) {
		Book existing = bookRepository.findById(book.getId()).orElseThrow();
		existing.setTitle(book.getTitle());
		existing.setAuthor(book.getAuthor());
		existing.setCategory(book.getCategory());
		existing.setPublisher(book.getPublisher());
		existing.setIsbn(book.getIsbn());
		existing.setCoverImageUrl(book.getCoverImageUrl());
		existing.setAvailable(book.isAvailable());
		return bookRepository.save(existing);
	}

	public boolean removeBookById(Long id) {
		Optional<Book> bookOptional = bookRepository.findById(id);
		if (bookOptional.isPresent()) {
			bookRepository.delete(bookOptional.get());
			return true;
		}
		return false;
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}

	public Page<Book> getBooks(String query, String category, String sort, int page, int size) {
		List<Book> books = loadBooks(query, category);
		sortBooks(books, sort);
		return slicePage(books, page, size);
	}

	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	public List<Book> searchBooksByTitle(String title) {
		return loadBooks(title, null);
	}

	public List<String> getAllCategories() {
		return bookRepository.findAll().stream()
			.map(Book::getCategory)
			.filter(category -> category != null && !category.isBlank())
			.distinct()
			.sorted(String.CASE_INSENSITIVE_ORDER)
			.toList();
	}

	public IssueRecord issueBook(Long bookId, Long userId, LocalDate expectedReturnDate, String remarks) {
		Book book = bookRepository.findById(bookId).orElseThrow();
		if (!book.isAvailable()) {
			throw new IllegalStateException("Book is already issued.");
		}
		User user = userRepository.findById(userId).orElseThrow();
		IssueRecord issueRecord = new IssueRecord();
		issueRecord.setBook(book);
		issueRecord.setUser(user);
		issueRecord.setIssueDate(LocalDate.now());
		issueRecord.setExpectedReturnDate(expectedReturnDate);
		issueRecord.setReturned(false);
		issueRecord.setRemarks(remarks);
		book.setAvailable(false);
		bookRepository.save(book);
		return issueRecordRepository.save(issueRecord);
	}

	public IssueRecord returnBook(Long issueRecordId) {
		IssueRecord issueRecord = issueRecordRepository.findById(issueRecordId).orElseThrow();
		if (!issueRecord.isReturned()) {
			issueRecord.setReturned(true);
			issueRecord.setReturnDate(LocalDate.now());
			issueRecord.getBook().setAvailable(true);
			bookRepository.save(issueRecord.getBook());
		}
		return issueRecordRepository.save(issueRecord);
	}

	public IssueRecord returnBookForCurrentUser(Long bookId, Long userId, boolean admin) {
		Book book = bookRepository.findById(bookId).orElseThrow();
		IssueRecord issueRecord = issueRecordRepository.findTopByBookAndReturnedFalseOrderByIssueDateDesc(book)
			.orElseThrow(() -> new IllegalStateException("No active issue record found."));
		if (!admin && !issueRecord.getUser().getId().equals(userId)) {
			throw new IllegalStateException("You can only return your own books.");
		}
		return returnBook(issueRecord.getId());
	}

	public List<IssueRecord> getRecentActivity() {
		return issueRecordRepository.findTop10ByOrderByIssueDateDesc();
	}

	public List<IssueRecord> getRecentIssuedBooks() {
		return issueRecordRepository.findTop10ByReturnedFalseOrderByIssueDateDesc();
	}

	public List<IssueRecord> getRecentReturnedBooks() {
		return issueRecordRepository.findTop10ByReturnedTrueOrderByReturnDateDesc();
	}

	public List<IssueRecord> getUserHistory(User user) {
		return issueRecordRepository.findByUserOrderByIssueDateDesc(user);
	}

	public long getTotalBooks() {
		return bookRepository.count();
	}

	public long getAvailableBooks() {
		return bookRepository.countByAvailableTrue();
	}

	public long getIssuedBooks() {
		return bookRepository.countByAvailableFalse();
	}

	public long getRegisteredUsers() {
		return userRepository.count();
	}

	public long getBooksIssuedToday() {
		return issueRecordRepository.countByIssueDate(LocalDate.now());
	}

	public long getBooksReturnedToday() {
		return issueRecordRepository.countByReturnDate(LocalDate.now());
	}

	public long getOverdueBooks() {
		return issueRecordRepository.findByReturnedFalseOrderByIssueDateDesc().stream()
			.filter(record -> record.getExpectedReturnDate() != null && record.getExpectedReturnDate().isBefore(LocalDate.now()))
			.count();
	}

	public Map<String, Long> getBooksByCategory() {
		Map<String, Long> categoryCounts = new LinkedHashMap<>();
		bookRepository.findAll().stream()
			.filter(book -> book.getCategory() != null && !book.getCategory().isBlank())
			.collect(Collectors.groupingBy(Book::getCategory, LinkedHashMap::new, Collectors.counting()))
			.entrySet().stream()
			.sorted(Map.Entry.comparingByKey(String.CASE_INSENSITIVE_ORDER))
			.forEach(entry -> categoryCounts.put(entry.getKey(), entry.getValue()));
		return categoryCounts;
	}

	public List<Map<String, Object>> getMostIssuedBooks() {
		Map<Long, Long> counts = issueRecordRepository.findAll().stream()
			.collect(Collectors.groupingBy(record -> record.getBook().getId(), Collectors.counting()));
		return counts.entrySet().stream()
			.sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
			.limit(5)
			.map(entry -> {
				Book book = bookRepository.findById(entry.getKey()).orElse(null);
				Map<String, Object> row = new LinkedHashMap<>();
				row.put("label", book == null ? "Unknown" : book.getTitle());
				row.put("value", entry.getValue());
				return row;
			})
			.toList();
	}

	public List<Map<String, Object>> getMostActiveUsers() {
		Map<Long, Long> counts = issueRecordRepository.findAll().stream()
			.collect(Collectors.groupingBy(record -> record.getUser().getId(), Collectors.counting()));
		return counts.entrySet().stream()
			.sorted(Map.Entry.<Long, Long>comparingByValue(Comparator.reverseOrder()))
			.limit(5)
			.map(entry -> {
				User user = userRepository.findById(entry.getKey()).orElse(null);
				Map<String, Object> row = new LinkedHashMap<>();
				row.put("label", user == null ? "Unknown" : user.getFullName());
				row.put("value", entry.getValue());
				return row;
			})
			.toList();
	}

	public Optional<IssueRecord> findActiveIssueForBook(Long bookId) {
		Book book = bookRepository.findById(bookId).orElse(null);
		if (book == null) {
			return Optional.empty();
		}
		return issueRecordRepository.findTopByBookAndReturnedFalseOrderByIssueDateDesc(book);
	}

	private List<Book> loadBooks(String query, String category) {
		List<Book> books;
		String normalizedQuery = query == null ? "" : query.trim();
		if (!normalizedQuery.isBlank()) {
			books = bookRepository.searchBooks(normalizedQuery);
		} else {
			books = new ArrayList<>(bookRepository.findAll());
		}
		String normalizedCategory = category == null ? "" : category.trim();
		if (!normalizedCategory.isBlank()) {
			books = books.stream()
				.filter(book -> book.getCategory() != null && book.getCategory().equalsIgnoreCase(normalizedCategory))
				.toList();
		}
		return new ArrayList<>(books);
	}

	private void sortBooks(List<Book> books, String sort) {
		String sortKey = sort == null ? "title" : sort;
		Comparator<Book> comparator = switch (sortKey) {
			case "author" -> Comparator.comparing(book -> safeSortValue(book.getAuthor()));
			case "category" -> Comparator.comparing(book -> safeSortValue(book.getCategory()));
			case "createdAt" -> Comparator.comparing(Book::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()));
			default -> Comparator.comparing(book -> safeSortValue(book.getTitle()));
		};
		books.sort(comparator);
	}

	private Page<Book> slicePage(List<Book> books, int page, int size) {
		int safePage = Math.max(page, 0);
		int safeSize = Math.max(size, 1);
		int start = Math.min(safePage * safeSize, books.size());
		int end = Math.min(start + safeSize, books.size());
		List<Book> content = books.subList(start, end);
		return new PageImpl<>(content, PageRequest.of(safePage, safeSize), books.size());
	}

	private String safeSortValue(String value) {
		return value == null ? "" : value.toLowerCase();
	}
}
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
