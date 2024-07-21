package com.tinqinacademy.hotel.persistence.repositories;

import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.persistence.mappers.GuestOutputRowMapper;
import com.tinqinacademy.hotel.persistence.mappers.GuestRowMapper;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class GuestRepository implements AliExpressJPARepository<Guest> {
    private final JdbcTemplate jdbcTemplate;
    private final GuestRowMapper guestRowMapper;
    private final GuestOutputRowMapper guestOutputRowMapper;
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

    public List<GuestOutput> searchGuests(GetGuestReportInput input) {
        StringBuilder sql = new StringBuilder("SELECT guests.*, bookings.start_date, bookings.end_date, users.phone_no," +
                " rooms.room_no FROM guests ");
        sql.append("JOIN guest_bookings ON guests.id = guest_bookings.guest_id ");
        sql.append("JOIN bookings ON guest_bookings.booking_id = bookings.id ");
        sql.append("JOIN users ON bookings.user_id = users.id ");
        sql.append("JOIN rooms ON bookings.room_id = rooms.id ");
        sql.append("WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (input.getStartDate() != null) {
            sql.append("AND bookings.start_date >= ? ");
            params.add(input.getStartDate());
        }

        if (input.getEndDate() != null) {
            sql.append("AND bookings.end_date <= ? ");
            params.add(input.getEndDate());
        }

        if (input.getFirstName() != null && !input.getFirstName().isEmpty()) {
            sql.append("AND guests.first_name LIKE ? ");
            params.add("%" + input.getFirstName() + "%");
        }

        if (input.getLastName() != null && !input.getLastName().isEmpty()) {
            sql.append("AND guests.last_name LIKE ? ");
            params.add("%" + input.getLastName() + "%");
        }

        if (input.getPhoneNo() != null && !input.getPhoneNo().isEmpty()) {
            sql.append("AND users.phone_no LIKE ? ");
            params.add("%" + input.getPhoneNo() + "%");
        }

        if (input.getIdCardNo() != null && !input.getIdCardNo().isEmpty()) {
            sql.append("AND guests.id_card_no LIKE ? ");
            params.add("%" + input.getIdCardNo() + "%");
        }

        if (input.getIdCardValidity() != null) {
            sql.append("AND guests.id_card_validity = ? ");
            params.add(input.getIdCardValidity());
        }

        if (input.getIdCardIssueAuthority() != null && !input.getIdCardIssueAuthority().isEmpty()) {
            sql.append("AND guests.id_card_issue_authority LIKE ? ");
            params.add("%" + input.getIdCardIssueAuthority() + "%");
        }

        if (input.getIdCardIssueDate() != null) {
            sql.append("AND guests.id_card_issue_date = ? ");
            params.add(input.getIdCardIssueDate());
        }

        if (input.getRoomNo() != null && !input.getRoomNo().isEmpty()) {
            sql.append("AND rooms.room_no LIKE ? ");
            params.add("%" + input.getRoomNo() + "%");
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), guestOutputRowMapper);
    }
}
