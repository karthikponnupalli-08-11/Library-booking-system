
package com.library.management.dto;

import com.library.management.entity.Reservation;

public class ReservationMapper {

    public static ReservationResponseDTO toDTO(
            Reservation reservation) {

        return new ReservationResponseDTO(
            reservation.getId(),

            reservation.getUser() != null
                ? reservation.getUser().getId() : null,

            reservation.getUser() != null
                ? reservation.getUser().getUsername() : null,

            reservation.getBook() != null
                ? reservation.getBook().getId() : null,

            reservation.getBook() != null
                ? reservation.getBook().getTitle() : null,

            reservation.getIssueDate(),
            reservation.getReturnDate(),
            reservation.getStatus()
        );
    }
}
