package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoom;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomInput;
import com.tinqinacademy.hotel.api.operations.searchroom.SearchRoomOutput;
import com.tinqinacademy.hotel.persistence.enumerations.BathroomType;
import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import com.tinqinacademy.hotel.persistence.specifications.RoomSpecification;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class SearchRoomProcessor extends BaseProcessor implements SearchRoom {
    private final RoomRepository roomRepository;
    private final RoomSpecification roomSpecification;

    public SearchRoomProcessor(ConversionService conversionService, Validator validator, RoomRepository roomRepository, RoomSpecification roomSpecification) {
        super(conversionService, validator);
        this.roomRepository = roomRepository;
        this.roomSpecification = roomSpecification;
    }


    @Override
    public Either<ErrorOutput, SearchRoomOutput> process(SearchRoomInput input) {
        log.info("Start searchRoom {}", input);

      return   Try.of(() -> {
            List<UUID> availableRoomIds = getAvailableRoomIds(input);

            SearchRoomOutput searchRoomOutput = SearchRoomOutput.builder()
                    .roomIds(availableRoomIds).build();
            log.info("End searchRoom {}", searchRoomOutput);
            return searchRoomOutput;
        }).toEither()
              .mapLeft(throwable -> Match(throwable).of(
                     defaultCase(throwable)
              ));


    }

    private List<UUID> getAvailableRoomIds(SearchRoomInput input) {
        log.info("Start getAvailableRoomIds {}", input);

        List<UUID> availableRoomIds = roomRepository.findAll(roomSpecification.searchForAvailableRooms(input.getStartDate(),
                        input.getEndDate(), input.getBedCount(), BedSize.getByCode(input.getBedSize().toString()),
                        BathroomType.getByCode(input.getBathroomType().toString()))).stream()
                .map(Room::getId).toList();

        log.info("End getAvailableRoomIds {}", availableRoomIds);

        return availableRoomIds;
    }
}
