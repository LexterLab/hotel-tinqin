package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.persistence.mappers.GuestRowMapper;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class GuestRepository implements AliExpressJPARepository<Guest> {
    private final JdbcTemplate jdbcTemplate;
    private final GuestRowMapper guestRowMapper;
    @Override
    public void save(Guest guest) {
        if (!existsByCardNo(guest.getIdCardNo())) {
            String sql = "INSERT INTO guests (id, first_name, last_name, birthday, id_card_no, id_card_validity, " +
                    "id_card_issue_authority, id_card_issue_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, guest.getId(), guest.getFirstName(), guest.getLastName(), guest.getBirthday(),
                    guest.getIdCardNo(), guest.getIdCardValidity(), guest.getIdCardIssueAuthority(), guest.getIdCardIssueDate());
        }
    }

    @Override
    public List<Guest> findAll() {
        return List.of();
    }

    @Override
    public Optional<Guest> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void updateById(UUID id, Guest guest) {

    }

    @Override
    public void saveAll(List<Guest> guests) {
        for (Guest guest : guests) {
            this.save(guest);
        }
    }

    @Override
    public void patchById(UUID id, Guest guest) {

    }

    public void addGuestToBooking(Guest guest, UUID bookingId) {
            String sql = "INSERT INTO guest_bookings (guest_id, booking_id) VALUES (?, ?)";
            jdbcTemplate.update(sql, guest.getId(), bookingId);
    }

    public boolean existsByCardNo(String cardNo) {
        String sql = "SELECT COUNT(*) FROM guests WHERE id_card_no = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, cardNo);
        return count > 0;
    }

    public Optional<Guest> findByCardNo(String cardNo) {
        String sql = "SELECT * FROM guests WHERE id_card_no = ?";
        Guest guest = jdbcTemplate.queryForObject(sql, new Object[]{cardNo}, guestRowMapper);
        return Optional.ofNullable(guest);
    }

    public boolean existsGuestBooking(UUID guestId, UUID bookingId) {
        String sql = "SELECT COUNT(*) FROM guest_bookings WHERE guest_id = ? AND booking_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, guestId, bookingId);
        return count > 0;
    }
}
