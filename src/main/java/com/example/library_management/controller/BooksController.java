package com.example.library_management.controller;

import com.example.library_management.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BooksController {

	private final LibraryService libraryService;

	public BooksController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@GetMapping("/books-ui")
	public String books(Model model) {
		model.addAttribute("books", libraryService.getAllBooks());
		model.addAttribute("searchTitle", "");
		return "books";
	}

	@GetMapping("/search-book")
	public String searchBooks(@RequestParam(required = false, defaultValue = "") String title, Model model) {
		model.addAttribute("books", libraryService.searchBooksByTitle(title));
		model.addAttribute("searchTitle", title);
		return "books";
	}

	@GetMapping("/delete-book/{id}")
	public String deleteBook(@PathVariable int id) {
		libraryService.removeBookById(id);
		return "redirect:/books-ui";
	}

	@GetMapping("/issue-book/{id}")
	public String issueBook(@PathVariable int id) {
		libraryService.issueBookById(id);
		return "redirect:/books-ui";
	}

	@GetMapping("/return-book/{id}")
	public String returnBook(@PathVariable int id) {
		libraryService.returnBookById(id);
		return "redirect:/books-ui";
	}
}