package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface GuestRepository extends JpaRepository<Guest, UUID> {

    Optional<Guest> findByIdCardNo(String idCardNo);
}
