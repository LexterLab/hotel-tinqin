package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.persistence.models.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = User.builder()
                .id(UUID.fromString(rs.getString("id")))
                .email(rs.getString("email"))
                .phoneNo(rs.getString("phone_no"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .password(rs.getString("password"))
                .build();

        return user;
    }
}
