package com.example.library_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * This class represents a book in the library management system.
 */
@Entity
@Table(name = "books")
 public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Column(nullable = false)
	private String title;

	@NotBlank(message = "Author is required")
	@Column(nullable = false)
	private String author;

	@NotBlank(message = "Category is required")
	@Column(nullable = false)
	private String category;

	@NotBlank(message = "Publisher is required")
	@Column(nullable = false)
	private String publisher;

	@NotBlank(message = "ISBN is required")
	@Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
	@Column(nullable = false, unique = true)
	private String isbn;

	@Column(length = 500)
	private String coverImageUrl;

	@Column(nullable = false)
	private boolean available = true;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void onCreate() {
		if (createdAt == null) {
			createdAt = LocalDateTime.now();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private boolean issued;

	/**
	 * This constructor creates an empty book object.
	 */
	public Book() {
	}

	/**
	 * This constructor creates a book with all details.
	 *
	 * @param id the book id
	 * @param title the book title
	 * @param author the book author
	 * @param issued the issued status of the book
	 */
	public Book(Long id, String title, String author, boolean issued) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.issued = issued;
	}

	/**
	 * This method returns the book id.
	 *
	 * @return the book id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method sets the book id.
	 *
	 * @param id the book id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method returns the book title.
	 *
	 * @return the book title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method sets the book title.
	 *
	 * @param title the book title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method returns the book author.
	 *
	 * @return the book author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * This method sets the book author.
	 *
	 * @param author the book author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * This method returns whether the book is issued or not.
	 *
	 * @return true if issued, otherwise false
	 */
	public boolean isIssued() {
		return issued;
	}

	/**
	 * This method sets the issued status of the book.
	 *
	 * @param issued the issued status
	 */
	public void setIssued(boolean issued) {
		this.issued = issued;
	}
}
