package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.persistence.mappers.BedRowMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BedRepository {
    private final JdbcTemplate jdbcTemplate;
    private final BedRowMapper bedRowMapper;

    public List<Bed> findAll() {
        return jdbcTemplate.query("select * from beds", new BeanPropertyRowMapper<>(Bed.class));
    }

    public Bed findById(UUID id) {
        String sql = "select * from beds where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Bed.class), id);
    }

    public Optional<Bed> findByBedSize(BedSize bedSize) {
        String sql = "select * from beds where bed_size = CAST(? AS BED);";
        List<Bed> beds = jdbcTemplate.query(sql, bedRowMapper, bedSize.toString());
        if (beds.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(beds.get(0));
        }
    }
    public void save(Bed bed) {
        String sql = "insert into beds (id, bed_size, bed_capacity) values (?, CAST(? AS BED), ?)";
            jdbcTemplate.update(sql, bed.getId(), bed.getBedSize().toString(), bed.getBedCapacity());
    }
}
