package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GuestOutputRowMapper implements RowMapper<GuestOutput> {
    @Override
    public GuestOutput mapRow(ResultSet rs, int rowNum) throws SQLException {
        GuestOutput output = GuestOutput
                .builder()
                .startDate(rs.getTimestamp("start_date").toLocalDateTime())
                .endDate(rs.getTimestamp("end_date").toLocalDateTime())
                .phoneNo(rs.getString("phone_no"))
                .idCardNo(rs.getString("id_card_no"))
                .idCardValidity(rs.getDate("id_card_validity").toLocalDate())
                .idCardIssueAuthority(rs.getString("id_card_issue_authority"))
                .idCardIssueDate(rs.getDate("id_card_issue_date").toLocalDate())
                .roomNo(rs.getString("room_no"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .build();

        return output;
    }
}
