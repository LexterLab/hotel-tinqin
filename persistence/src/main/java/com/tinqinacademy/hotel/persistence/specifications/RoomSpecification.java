package com.tinqinacademy.hotel.persistence.specifications;


import com.tinqinacademy.hotel.persistence.enumerations.BathroomType;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RoomSpecification {

    public Specification<Room> searchForAvailableRooms(LocalDateTime startDate, LocalDateTime endDate,
                                                       Integer bedCount, BedSize bedSize, BathroomType bathroomType) {
        return (root, query, cb) -> {

        Subquery<UUID> excludedRoomIdsSubquery = query.subquery(UUID.class);
        Root<Booking> excludedBooking = excludedRoomIdsSubquery.from(Booking.class);
        excludedRoomIdsSubquery.select(excludedBooking.get("room").get("id"));
        Predicate overlappingBookingsPredicate = cb.and(
                cb.lessThanOrEqualTo(excludedBooking.get("startDate"), endDate),
                cb.greaterThanOrEqualTo(excludedBooking.get("endDate"), startDate)
        );
        excludedRoomIdsSubquery.where(overlappingBookingsPredicate);

        Subquery<Long> bedSizeSubquery = query.subquery(Long.class);
        Root<Room> subqueryRoom = bedSizeSubquery.from(Room.class);
        Join<Room, Bed> subqueryRoomBedJoin = subqueryRoom.join("beds", JoinType.INNER);
        bedSizeSubquery.select(cb.literal(1L));
        Predicate bedSizePredicate = cb.and(
                cb.equal(subqueryRoomBedJoin.get("bedSize"), bedSize),
                cb.equal(subqueryRoom.get("id"), root.get("id"))
        );
        bedSizeSubquery.where(bedSizePredicate);

        Predicate mainPredicate = cb.conjunction();

        mainPredicate = cb.and(mainPredicate, cb.not(root.get("id").in(excludedRoomIdsSubquery)));

        if (bedCount != null) {
            mainPredicate = cb.and(mainPredicate, cb.equal(cb.size(root.get("beds")), bedCount));
        }

        if (bedSize != null) {
            mainPredicate = cb.and(mainPredicate, cb.exists(bedSizeSubquery));
        }

        if (bathroomType != null) {
            mainPredicate = cb.and(mainPredicate, cb.equal(root.get("bathroomType"), bathroomType));
        }

        return mainPredicate;
    };
}
}