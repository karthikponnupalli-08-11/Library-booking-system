	package com.library.management.entity;

	import java.time.LocalDate;

	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.Table;

	@Entity
	@Table(name = "reservations")
	public class Reservation {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User user;

	    @ManyToOne
	    @JoinColumn(name = "book_id")
	    private Book book;

	    private LocalDate issueDate;

	    private LocalDate returnDate;

	    private String status = "PENDING";

	    // Getters and Setters

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public Book getBook() {
	        return book;
	    }

	    public void setBook(Book book) {
	        this.book = book;
	    }

	    public LocalDate getIssueDate() {
	        return issueDate;
	    }

	    public void setIssueDate(LocalDate issueDate) {
	        this.issueDate = issueDate;
	    }

	    public LocalDate getReturnDate() {
	        return returnDate;
	    }

	    public void setReturnDate(LocalDate returnDate) {
	        this.returnDate = returnDate;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }
	}
