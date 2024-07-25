package com.tinqinacademy.hotel.core.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.contracts.SystemService;
import com.tinqinacademy.hotel.core.mappers.GuestMapper;
import com.tinqinacademy.hotel.core.mappers.RoomMapper;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.models.room.Room;

import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BedRepository bedRepository;
    private final BookingRepository bookingRepository;
    private final ObjectMapper objectMapper;

    @Override
    public RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Start registerVisitor {}", input);

        List<Guest> guests = GuestMapper.INSTANCE.guestInputToGuestList(input.getGuests());

        Booking booking = bookingRepository.findById(input.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", input.getBookingId().toString()));

        for (Guest guest : guests) {
            Optional<Guest> existingGuest = guestRepository.findByIdCardNo(guest.getIdCardNo());
            if (existingGuest.isPresent()) {
                if (booking.getGuests().contains(existingGuest.get())) {
                    throw new GuestAlreadyRegisteredException(guest.getId(), input.getBookingId());
                }
                booking.getGuests().add(existingGuest.get());
                bookingRepository.save(booking);
            } else {
                booking.setGuests(List.of(guest));
                guestRepository.save(guest);
            }


        }

        RegisterGuestOutput output = RegisterGuestOutput.builder().build();
        log.info("End registerVisitor {}", output);
        return output;
    }

    @Override
    public GetGuestReportOutput getGuestReport(GetGuestReportInput input) {
        log.info("Start getVisitorsReport {}", input);

        List<Guest> guests = guestRepository.searchGuest(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getPhoneNo(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo());

        GetGuestReportOutput output = GetGuestReportOutput.builder()
                .guestsReports(GuestMapper.INSTANCE.guestToGuestOutput(guests))
                .build();

        log.info("End getVisitorsReport {}", output);
        return output;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        if (roomRepository.countAllByRoomNo(input.roomNo()) > 0) {
            throw new RoomNoAlreadyExistsException(input.roomNo());
        }

        List<Bed> roomBeds = new ArrayList<>();

        for (BedSize size : input.beds()) {
            Bed bed = bedRepository.findByBedSize(size)
                    .orElseThrow(() -> new ResourceNotFoundException("Bed", "bedSize", size.toString()));
            roomBeds.add(bed);
        }

        Room room = RoomMapper.INSTANCE.createRoomInputToRoom(input);
        room.setBeds(roomBeds);

        roomRepository.save(room);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .roomId(room.getId().toString())
                .build();

        log.info("End createRoom {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        if (roomRepository.countAllByRoomNo(input.getRoomNo()) > 0 && !room.getRoomNo().equals(input.getRoomNo())) {
            throw new RoomNoAlreadyExistsException(input.getRoomNo());
        }

        List<Bed> roomBeds = new ArrayList<>();

        for (BedSize size : input.getBeds()) {
            Bed bed = bedRepository.findByBedSize(size)
                    .orElseThrow(() -> new ResourceNotFoundException("Bed", "bedSize", size.toString()));
            roomBeds.add(bed);
        }

        RoomMapper.INSTANCE.updateRoom(room, input);
        room.setBeds(roomBeds);

        roomRepository.save(room);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();
        log.info("End updateRoom {}", output);
        return output;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) throws JsonPatchException, JsonProcessingException {
        log.info("Start partialUpdateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        if (input.getRoomNo() != null) {
            if (roomRepository.countAllByRoomNo(input.getRoomNo()) > 0 && !room.getRoomNo().equals(input.getRoomNo())) {
                throw new RoomNoAlreadyExistsException(input.getRoomNo());
            }
        }

        JsonNode roomNode = objectMapper.convertValue(room, JsonNode.class);
        JsonNode inputNode = objectMapper.convertValue(input, JsonNode.class);

        ((ObjectNode) inputNode).remove("roomId");
        ((ObjectNode) inputNode).remove("beds");

        JsonMergePatch mergePatch = JsonMergePatch.fromJson(inputNode);

        JsonNode patchedRoomNode = mergePatch.apply(roomNode);

        Room patchedRoom = objectMapper.treeToValue(patchedRoomNode, Room.class);
        patchedRoom.setId(room.getId());


        if (input.getBeds() != null) {
            List<Bed> roomBeds = new ArrayList<>();
            for (BedSize size : input.getBeds()) {
                Bed bed = bedRepository.findByBedSize(size)
                        .orElseThrow(() -> new ResourceNotFoundException("Bed", "bedSize", size.toString()));
                roomBeds.add(bed);
            }
            patchedRoom.setBeds(roomBeds);
        }

        roomRepository.save(patchedRoom);

        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();

        log.info("End partialUpdateRoom {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);
        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        bookingRepository.deleteAll(room.getBookings());

        roomRepository.delete(room);
        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("End deleteRoom {}", output);
        return output;
    }
}
