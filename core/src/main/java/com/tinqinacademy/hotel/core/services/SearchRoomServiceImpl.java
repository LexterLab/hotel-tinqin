package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.SearchRoomService;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BathroomType;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.specifications.RoomSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchRoomServiceImpl implements SearchRoomService {
    private final RoomRepository roomRepository;
    private final RoomSpecification roomSpecification;

    @Override
    public SearchRoomOutput searchRoom(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);

        List<UUID> availableRoomIds = roomRepository.findAll(roomSpecification.searchForAvailableRooms(input.getStartDate(),
                        input.getEndDate(), input.getBedCount(), BedSize.getByCode(input.getBedSize().toString()),
                        BathroomType.getByCode(input.getBathroomType().toString()))).stream()
                .map(Room::getId).toList();

        SearchRoomOutput searchRoomOutput = SearchRoomOutput.builder()
                .roomIds(availableRoomIds).build();
        log.info("End searchRoom {}", searchRoomOutput);
        return searchRoomOutput;
    }
}
