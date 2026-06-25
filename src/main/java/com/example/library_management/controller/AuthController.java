package com.example.library_management.controller;

import com.example.library_management.model.Role;
import com.example.library_management.model.User;
import com.example.library_management.service.LibraryService;
import com.example.library_management.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

	private final UserService userService;
	private final LibraryService libraryService;

	public AuthController(UserService userService, LibraryService libraryService) {
		this.userService = userService;
		this.libraryService = libraryService;
	}

	@GetMapping("/login")
	public String loginPage(Principal principal) {
		if (principal != null) {
			return "redirect:/dashboard";
		}
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage(Model model, Principal principal) {
		if (principal != null) {
			return "redirect:/dashboard";
		}
		if (!model.containsAttribute("user")) {
			User user = new User();
			user.setRole(Role.USER);
			model.addAttribute("user", user);
		}
		return "signup";
	}

	@PostMapping("/signup")
	public String registerUser(@Valid @ModelAttribute("user") User user,
			@RequestParam String confirmPassword,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes) {
		if (!user.isAcceptedTerms()) {
			bindingResult.rejectValue("acceptedTerms", "acceptedTerms", "You must accept the terms to continue");
		}
		if (!confirmPassword.equals(user.getPassword())) {
			bindingResult.rejectValue("password", "passwordMismatch", "Passwords do not match");
		}
		if (bindingResult.hasErrors()) {
			user.setPassword("");
			model.addAttribute("confirmPassword", confirmPassword);
			return "signup";
		}
		try {
			userService.registerUser(user, confirmPassword);
			redirectAttributes.addFlashAttribute("successMessage", "Account created successfully. Please login.");
			return "redirect:/login";
		} catch (RuntimeException ex) {
			model.addAttribute("signupError", ex.getMessage());
			user.setPassword("");
			model.addAttribute("confirmPassword", confirmPassword);
			return "signup";
		}
	}

	@GetMapping("/forgot-password")
	public String forgotPasswordPage() {
		return "forgot-password";
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("forgotPasswordMessage", "If this email exists, a password reset link will be sent.");
		return "redirect:/forgot-password";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElse(null));
		model.addAttribute("totalBooks", libraryService.getTotalBooks());
		model.addAttribute("availableBooks", libraryService.getAvailableBooks());
		model.addAttribute("issuedBooks", libraryService.getIssuedBooks());
		return "dashboard";
	}

	@GetMapping("/profile")
	public String profile(Model model, Principal principal) {
		model.addAttribute("currentUser", userService.findByEmail(principal.getName()).orElse(null));
		return "profile";
	}

	@PostMapping("/profile")
	public String updateProfile(@RequestParam String fullName,
			@RequestParam String phoneNumber,
			Principal principal,
			RedirectAttributes redirectAttributes) {
		User user = userService.findByEmail(principal.getName()).orElseThrow();
		user.setFullName(fullName);
		user.setPhoneNumber(phoneNumber);
		userService.updateProfile(user);
		redirectAttributes.addFlashAttribute("profileMessage", "Profile updated successfully.");
		return "redirect:/profile";
	}

	@GetMapping("/change-password")
	public String changePasswordPage() {
		return "change-password";
	}

	@PostMapping("/change-password")
	public String changePassword(@RequestParam String currentPassword,
			@RequestParam String newPassword,
			@RequestParam String confirmPassword,
			Principal principal,
			RedirectAttributes redirectAttributes) {
		User user = userService.findByEmail(principal.getName()).orElseThrow();
		User authenticatedUser = userService.authenticateUser(user.getEmail(), currentPassword);
		if (authenticatedUser == null || !authenticatedUser.getId().equals(user.getId())) {
			redirectAttributes.addFlashAttribute("passwordError", "Current password is incorrect.");
			return "redirect:/change-password";
		}
		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("passwordError", "New passwords do not match.");
			return "redirect:/change-password";
		}
		userService.updatePassword(user, newPassword);
		redirectAttributes.addFlashAttribute("passwordMessage", "Password changed successfully.");
		return "redirect:/change-password";
	}
}