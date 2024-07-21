package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class GuestRowMapper implements RowMapper<Guest> {
    @Override
    public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Guest guest = Guest
                .builder()
                .id(UUID.fromString(rs.getString("id")))
                .birthday(rs.getDate("birthday").toLocalDate())
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .idCardIssueAuthority(rs.getString("id_card_issue_authority"))
                .idCardIssueDate(rs.getDate("id_card_issue_date").toLocalDate())
                .idCardNo(rs.getString("id_card_no"))
                .idCardValidity(rs.getDate("id_card_validity").toLocalDate())
                .build();
        return guest;
    }
}
