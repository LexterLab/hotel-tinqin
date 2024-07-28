package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Component
public class RoomToGetRoomOutput extends AbstractConverter<Room, GetRoomOutput> {
    @Override
    protected Class<GetRoomOutput> getTargetClass() {
        return GetRoomOutput.class;
    }

    @Override
    protected GetRoomOutput doConvert(Room source) {
        List<BedSize> bedSizes = source.getBeds().stream()
                .map(Bed::getBedSize)
                .map(bedSize -> BedSize.getByCode(bedSize.toString()))
                .toList();

        List<LocalDateTime> datesOccupied = source.getBookings()
                .stream()
                .flatMap(booking -> Stream.of(booking.getStartDate(), booking.getEndDate()))
                .distinct()
                .toList();

        GetRoomOutput output = GetRoomOutput
                .builder()
                .id(source.getId())
                .price(source.getPrice())
                .floor(source.getFloor())
                .bedSizes(bedSizes)
                .bathroomType(BathroomType.getByCode(source.getBathroomType().toString()))
                .bedCount(bedSizes.size())
                .datesOccupied(datesOccupied)
                .build();

        return output;
    }
}
