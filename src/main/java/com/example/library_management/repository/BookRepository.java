package com.example.library_management.repository;

import com.example.library_management.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findByTitleContainingIgnoreCase(String title);

	List<Book> findByCategoryContainingIgnoreCase(String category);

	long countByAvailableTrue();

	long countByAvailableFalse();

	List<Book> findTop6ByOrderByCreatedAtDesc();

	@Query("""
		select b from Book b
		where lower(b.title) like lower(concat('%', :query, '%'))
		   or lower(b.author) like lower(concat('%', :query, '%'))
		   or lower(b.category) like lower(concat('%', :query, '%'))
		   or lower(b.publisher) like lower(concat('%', :query, '%'))
		   or lower(b.isbn) like lower(concat('%', :query, '%'))
	""")
	List<Book> searchBooks(@Param("query") String query);
}
