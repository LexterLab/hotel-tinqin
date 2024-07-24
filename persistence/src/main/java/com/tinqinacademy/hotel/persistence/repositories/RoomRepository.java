package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface RoomRepository extends JpaRepository<Room, UUID> {
    long countAllByRoomNo(String roomNo);

    @Query(value = """
            SELECT r FROM Room r
            JOIN r.bookings b
            JOIN r.beds bed
            WHERE (r.id NOT IN (
                SELECT r.id FROM Booking b
                WHERE (b.startDate <= :endDate AND b.endDate >= :startDate)
            ))
            AND (:bedCount IS NULL OR (SELECT COUNT(bed) FROM r.beds bed) = :bedCount)
            AND (:bedSize IS NULL  OR bed.bedSize = :bedSize)
            AND (:bathroomType IS NULL OR b.room.bathroomType = :bathroomType)
            """)
    List<Room> searchForAvailableRooms(LocalDateTime startDate, LocalDateTime endDate, Integer bedCount,
                                       BedSize bedSize, BathroomType bathroomType);
}
