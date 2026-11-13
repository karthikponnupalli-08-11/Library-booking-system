package com.library.management.service;

import com.library.management.config.CurrentUserService;
import com.library.management.dto.ReservationMapper;
import com.library.management.dto.ReservationResponseDTO;
import com.library.management.entity.Book;
import com.library.management.entity.Reservation;
import com.library.management.entity.User;
import com.library.management.exception.BadRequestException;
import com.library.management.exception.BookNotAvailableException;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.exception.UserNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.ReservationRepository;
import com.library.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final CurrentUserService currentUserService;

    public ReservationServiceImpl(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            CurrentUserService currentUserService) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    private Reservation createReservation(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User ID " + userId + " not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book ID " + bookId + " not found"));

        if (book.getQuantity() <= 0) {
            throw new BookNotAvailableException("Book is currently out of stock");
        }

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setIssueDate(LocalDate.now());
        reservation.setReturnDate(LocalDate.now().plusDays(7));
        reservation.setStatus("ISSUED");

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation bookBook(Long userId, Long bookId) {
        return createReservation(userId, bookId);
    }

    @Override
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation ID " + id + " not found"));
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public List<Reservation> getReservationsByBookId(Long bookId) {
        return reservationRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public Reservation returnBook(Long reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!"ISSUED".equals(reservation.getStatus())) {
            throw new BadRequestException("Book is already returned or not issued");
        }

        reservation.setStatus("RETURNED");

        Book book = reservation.getBook();
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        }

        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public ReservationResponseDTO booksBook(Long userId, Long bookId) {
        Reservation reservation = createReservation(userId, bookId);
        return ReservationMapper.toDTO(reservation);
    }

    @Override
    public ReservationResponseDTO getReservationByIda(Long id) {
        Reservation reservation = getReservationById(id);
        return ReservationMapper.toDTO(reservation);
    }

    @Override
    public List<ReservationResponseDTO> getAllReservationss() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByUserIda(Long userId) {
        return reservationRepository.findByUserId(userId)
                .stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationResponseDTO> getReservationsByBookIda(Long bookId) {
        return reservationRepository.findByBookId(bookId)
                .stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponseDTO returnBooka(Long reservationId) {
        Reservation reservation = returnBook(reservationId);
        return ReservationMapper.toDTO(reservation);
    }

    @Override
    public List<Reservation> getMyReservations() {
        String username = currentUserService.getLoggedInUsername();
        return reservationRepository.findByUserUsername(username);
    }

    public Reservation returnMyBook(Long reservationId) {
        String username = currentUserService.getLoggedInUsername();

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (reservation.getUser() == null
                || !reservation.getUser().getUsername().equals(username)) {
            throw new BadRequestException("You cannot return another user's book");
        }

        return returnBook(reservationId);
    }
}