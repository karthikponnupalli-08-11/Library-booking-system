package com.library.management.service;

import com.library.management.dto.ReservationResponseDTO;
import com.library.management.entity.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation bookBook(Long userId, Long bookId);

    Reservation getReservationById(Long id);

    List<Reservation> getAllReservations();

    List<Reservation> getReservationsByUserId(Long userId);

    List<Reservation> getReservationsByBookId(Long bookId);

    Reservation returnBook(Long reservationId);

    ReservationResponseDTO booksBook(Long userId, Long bookId);

    ReservationResponseDTO getReservationByIda(Long id);

    List<ReservationResponseDTO> getAllReservationss();

    List<ReservationResponseDTO> getReservationsByUserIda(Long userId);

    List<ReservationResponseDTO> getReservationsByBookIda(Long bookId);

    ReservationResponseDTO returnBooka(Long reservationId);

    List<Reservation> getMyReservations();
}