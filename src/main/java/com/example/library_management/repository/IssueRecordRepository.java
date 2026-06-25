package com.example.library_management.repository;

import com.example.library_management.model.Book;
import com.example.library_management.model.IssueRecord;
import com.example.library_management.model.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRecordRepository extends JpaRepository<IssueRecord, Long> {

	List<IssueRecord> findTop10ByOrderByIssueDateDesc();

	List<IssueRecord> findTop10ByReturnedFalseOrderByIssueDateDesc();

	List<IssueRecord> findTop10ByReturnedTrueOrderByReturnDateDesc();

	List<IssueRecord> findByUserOrderByIssueDateDesc(User user);

	List<IssueRecord> findByReturnedFalseOrderByIssueDateDesc();

	Optional<IssueRecord> findTopByBookAndReturnedFalseOrderByIssueDateDesc(Book book);

	long countByIssueDate(LocalDate issueDate);

	long countByReturnDate(LocalDate returnDate);

	long countByReturnedFalse();

	long countByReturnedTrue();

	List<IssueRecord> findTop5ByOrderByIssueDateDesc();
}