package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

//    @Query(value = """
//            SELECT r.id FROM Room r
//            JOIN r.bookings b
//            JOIN r.beds bed
//            WHERE (r.id NOT IN (
//                SELECT r.id FROM Booking b
//                WHERE (b.startDate <= :endDate AND b.endDate >= :startDate)
//            ))
//            AND (:bedCount IS NULL OR (SELECT COUNT(bed) FROM r.beds bed) = :bedCount)
//            AND (:bedSize IS NULL OR bed.bedSize = :bedSize)
//            AND (:bathroomType IS NULL OR b.room.bathroomType = :bathroomType)
//            """)
//    List<Room> searchForAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Integer bedCount,
//                                       BedSize bedSize, BathroomType bathroomType);

//    @Query(value = """
//    SELECT DISTINCT r.id FROM rooms r
//    JOIN room_beds rb ON r.id = rb.room_id
//    JOIN beds b ON rb.bed_id = b.id
//    WHERE r.id NOT IN (
//        SELECT room_id FROM bookings
//        WHERE (start_date < :endDate AND end_date > :startDate)
//    )
//    AND (:bathroomType IS NULL OR r.bathroom_type = :bathroomType)
//    AND (:bedSize IS NULL OR b.bed_size = :bedSize)
//    AND (:bedCount IS NULL OR (
//        SELECT COUNT(*) FROM room_beds rb2 WHERE rb2.room_id = r.id
//    ) = :bedCount)
//    """, nativeQuery = true)
//    List<UUID> searchForAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Integer bedCount,
//                                       BedSize bedSize, BathroomType bathroomType);
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


    Optional<Booking> findLatestByRoomIdAndUserId(UUID roomId, UUID userId);
}
