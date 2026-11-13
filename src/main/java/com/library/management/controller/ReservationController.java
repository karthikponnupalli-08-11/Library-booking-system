
package com.library.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.library.management.dto.ReservationResponseDTO;
import com.library.management.entity.Reservation;
import com.library.management.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    // Constructor Injection
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // 1. Book a book
    @PostMapping("/books/{userId}/{bookId}")
    public ResponseEntity<ReservationResponseDTO> booksBook(
            @PathVariable Long userId,
            @PathVariable Long bookId) {

        return ResponseEntity.ok(
            reservationService.booksBook(userId, bookId));
    }
    

    // 2. Get all reservations
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getAllReservationss() {

        return ResponseEntity.ok(
            reservationService.getAllReservationss());
    }

    // 3. Get reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.getReservationById(id));
    }

    // 4. Get reservations by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                reservationService.getReservationsByUserId(userId));
    }

    // 5. Get reservations by book ID
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Reservation>> getReservationsByBookId(
            @PathVariable Long bookId) {

        return ResponseEntity.ok(
                reservationService.getReservationsByBookId(bookId));
    }

    // 6. Return a book
    @PutMapping("/return/{id}")
    public ResponseEntity<Reservation> returnBook(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                reservationService.returnBook(id));
    }
    
}
