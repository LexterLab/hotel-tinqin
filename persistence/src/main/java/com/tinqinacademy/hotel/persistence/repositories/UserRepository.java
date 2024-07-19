package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.models.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepository implements AliExpressJPARepository<User> {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public void save(User user) {
        String sql = "insert into users (id, password, email," +
                " phone_no, first_name, last_name) values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPassword(), user.getEmail(), user.getPhoneNo(),
                user.getFirstName(), user.getLastName());
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void updateById(UUID id, User user) {

    }

    @Override
    public void saveAll(List<User> users) {

    }

    public boolean existByUsername(String username) {
        String sql = "select count(*) from users where username = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, username);
        return count > 0;
    }

    public boolean existByEmail(String email) {
        String sql = "select count(*) from users where email = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, email);
        return count > 0;
    }
}