package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BedRepository extends JpaRepository<Bed, UUID> {
    Optional<Bed> findByBedSize(BedSize bedSize);
}
