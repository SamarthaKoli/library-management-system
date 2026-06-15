package com.example.library_management.controller;

import com.example.library_management.model.Book;
import com.example.library_management.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddBookController {

	private final LibraryService libraryService;

	public AddBookController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@GetMapping("/add-book")
	public String showAddBookForm(Model model) {
		model.addAttribute("book", new Book());
		return "add-book";
	}

	@PostMapping("/add-book")
	public String addBook(@ModelAttribute Book book) {
		libraryService.addBook(book);
		return "redirect:/books-ui";
	}
}