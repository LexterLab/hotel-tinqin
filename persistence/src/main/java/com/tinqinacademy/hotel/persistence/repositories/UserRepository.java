package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    long countByEmail(String email);
}
