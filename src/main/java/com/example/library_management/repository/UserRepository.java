package com.example.library_management.repository;

import com.example.library_management.model.User;
import com.example.library_management.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	long countByRole(Role role);
}