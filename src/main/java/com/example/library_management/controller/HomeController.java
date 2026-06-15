package com.example.library_management.controller;

import com.example.library_management.service.LibraryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private final LibraryService libraryService;

	public HomeController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("totalBooks", libraryService.getTotalBooks());
		model.addAttribute("issuedBooks", libraryService.getIssuedBooks());
		model.addAttribute("availableBooks", libraryService.getAvailableBooks());
		return "index";
	}
}