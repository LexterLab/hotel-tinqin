package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Query("""
        SELECT b FROM Booking b
        JOIN b.guests g
        JOIN b.room r
        WHERE (CAST(:startDate AS DATE) IS NULL OR b.startDate >= :startDate)
        AND (CAST(:endDate AS DATE) IS NULL OR b.endDate <= :endDate)
        AND (:userId IS NULL OR b.userId = :userId)
        AND (:firstName IS NULL OR g.firstName LIKE :firstName)
        AND (:lastName IS NULL OR g.lastName LIKE :lastName)
        AND (:idCardNo IS NULL OR g.idCardNo LIKE :idCardNo)
        AND (CAST(:idCardValidity AS DATE) IS NULL OR g.idCardValidity = :idCardValidity)
        AND (:idCardIssueAuthority IS NULL OR g.idCardIssueAuthority = :idCardIssueAuthority)
        AND (CAST(:idCardIssueDate AS DATE) IS NULL OR g.idCardIssueDate = :idCardIssueDate)
        AND (:roomNo IS NULL OR r.roomNo = :roomNo)
    """)
    List<Booking> searchBookings(LocalDateTime startDate, LocalDateTime endDate, String firstName, String lastName,
                           String idCardNo, LocalDate idCardValidity, String idCardIssueAuthority,
                            LocalDate idCardIssueDate, String roomNo, UUID userId);

    @Query(value = """
        SELECT b FROM Booking b
        WHERE b.room.id = :roomId
        AND b.startDate >= CURRENT DATE
        ORDER BY b.startDate DESC
    """)
    List<Booking> findBookingsByRoomIdAndCurrentDate(UUID roomId);

    @Query(value = """
    SELECT COUNT(b) FROM Booking b
    WHERE (b.room.id = :roomId)
    AND (b.startDate = :startDate AND b.endDate = :endDate)
    OR (b.startDate BETWEEN :startDate AND :endDate)
    OR (b.endDate BETWEEN :startDate AND :endDate)
    """)
    Long countByRoomAndDates(UUID roomId, LocalDateTime startDate, LocalDateTime endDate);


    Optional<Booking> findBookingByIdAndUserId(UUID id, UUID userId);
}
