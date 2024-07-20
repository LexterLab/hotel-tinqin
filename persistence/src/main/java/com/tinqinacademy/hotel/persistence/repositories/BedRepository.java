package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.persistence.mappers.BedRowMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BedRepository implements AliExpressJPARepository<Bed> {
    private final JdbcTemplate jdbcTemplate;
    private final BedRowMapper bedRowMapper;

    @Override
    public List<Bed> findAll() {
        return jdbcTemplate.query("select * from beds", bedRowMapper);
    }

    public Optional<Bed> findById(UUID id) {
        String sql = "select * from beds where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, bedRowMapper, id));
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void updateById(UUID id, Bed bed) {

    }

    @Override
    public void saveAll(List<Bed> beds) {

    }

    @Override
    public void patchById(UUID id, Bed bed) {

    }

    public Optional<Bed> findByBedSize(BedSize bedSize) {
        String sql = "select * from beds where bed_size = CAST(? AS BED);";
        List<Bed> beds = jdbcTemplate.query(sql, bedRowMapper, bedSize.toString());
        if (beds.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(beds.getFirst());
        }
    }

    @Override
    public void save(Bed bed) {
        String sql = "insert into beds (id, bed_size, bed_capacity) values (?, CAST(? AS BED), ?)";
            jdbcTemplate.update(sql, bed.getId(), bed.getBedSize().toString(), bed.getBedCapacity());
    }
}
