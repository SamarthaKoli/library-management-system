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
