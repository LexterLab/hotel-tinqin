package com.tinqinacademy.hotel.persistence.mappers;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class BedRowMapper implements RowMapper<Bed> {
    @Override
    public Bed mapRow(ResultSet rs, int rowNum) throws SQLException {
        Bed bed = Bed.builder()
                .id(UUID.fromString(rs.getString("id")))
                .bedCapacity(rs.getInt("bed_capacity"))
                .bedSize(BedSize.getByCode(rs.getString("bed_size")))
                .build();

        return bed;
    }
}
