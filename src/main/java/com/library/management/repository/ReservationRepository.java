package com.library.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.management.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Find reservations by user ID
    List<Reservation> findByUserId(Long userId);

    // Find reservations by book ID
    List<Reservation> findByBookId(Long bookId);

    // Find reservations by status
    List<Reservation> findByStatus(String status);
    List<Reservation> findByUserUsername(String username);
    


}