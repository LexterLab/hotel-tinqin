package com.tinqinacademy.hotel.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AliExpressJPARepository<T> {
    void save(T t);
    List<T> findAll();
    Optional<T> findById(UUID id);
}