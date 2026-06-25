package com.example.library_management.service;

import com.example.library_management.model.Role;
import com.example.library_management.model.User;
import com.example.library_management.repository.UserRepository;
import jakarta.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public User registerUser(User user, String confirmPassword) {
		user.setEmail(normalizeEmail(user.getEmail()));
		validateEmail(user.getEmail());
		if (!user.getPassword().equals(confirmPassword)) {
			throw new ValidationException("Passwords do not match");
		}
		if (checkDuplicateEmail(user.getEmail())) {
			throw new ValidationException("Email already exists");
		}
		user.setPassword(encryptPassword(user.getPassword()));
		user.setRole(user.getRole() == null ? Role.USER : user.getRole());
		return userRepository.save(user);
	}

	public User loginUser(String email, String password) {
		User user = authenticateUser(email, password);
		if (user == null) {
			throw new BadCredentialsException("Invalid email or password");
		}
		return user;
	}

	public String encryptPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void validateEmail(String email) {
		String normalizedEmail = normalizeEmail(email);
		if (normalizedEmail == null || !normalizedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
			throw new ValidationException("Invalid email address");
		}
	}

	public boolean checkDuplicateEmail(String email) {
		return userRepository.existsByEmail(normalizeEmail(email));
	}

	public User authenticateUser(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmail(normalizeEmail(email));
		if (optionalUser.isEmpty()) {
			return null;
		}
		User user = optionalUser.get();
		return passwordEncoder.matches(password, user.getPassword()) ? user : null;
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public long countUsers() {
		return userRepository.count();
	}

	public User updateProfile(User user) {
		return userRepository.save(user);
	}

	public User updatePassword(User user, String rawPassword) {
		user.setPassword(encryptPassword(rawPassword));
		return userRepository.save(user);
	}

	private String normalizeEmail(String email) {
		return email == null ? null : email.trim().toLowerCase();
	}
}