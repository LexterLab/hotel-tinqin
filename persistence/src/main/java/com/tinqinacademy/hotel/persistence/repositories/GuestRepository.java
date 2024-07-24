package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface GuestRepository extends JpaRepository<Guest, UUID> {

    Optional<Guest> findByIdCardNo(String idCardNo);

    @Query("""
        SELECT g FROM Booking b
        JOIN b.guests g
        JOIN b.user u
        JOIN b.room r
        WHERE (CAST(:startDate AS DATE) IS NULL OR b.startDate >= :startDate)
        AND (CAST(:endDate AS DATE) IS NULL OR b.endDate <= :endDate)
        AND (:firstName IS NULL OR g.firstName LIKE :firstName)
        AND (:lastName IS NULL OR g.lastName LIKE :lastName)
        AND (:phoneNo IS NULL OR u.phoneNo LIKE :phoneNo)
        AND (:idCardNo IS NULL OR g.idCardNo LIKE :idCardNo)
        AND (CAST(:idCardValidity AS DATE) IS NULL OR g.idCardValidity = :idCardValidity)
        AND (:idCardIssueAuthority IS NULL OR g.idCardIssueAuthority = :idCardIssueAuthority)
        AND (CAST(:idCardIssueDate AS DATE) IS NULL OR g.idCardIssueDate = :idCardIssueDate)
        AND (:roomNo IS NULL OR r.roomNo = :roomNo)
    """)
    List<Guest> searchGuest(LocalDateTime startDate, LocalDateTime endDate, String firstName, String lastName,
                            String phoneNo, String idCardNo, LocalDate idCardValidity, String idCardIssueAuthority,
                            LocalDate idCardIssueDate, String roomNo);
}
