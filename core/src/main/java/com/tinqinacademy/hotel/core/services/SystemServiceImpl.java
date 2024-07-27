package com.tinqinacademy.hotel.core.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialRoomUpdate;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.api.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.models.room.Room;

import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

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
    private final ConversionService conversionService;

    @Override
    @Transactional
    public RegisterGuestOutput registerGuest(RegisterGuestInput input) {
        log.info("Start registerVisitor {}", input);

        List<Guest> guests = input.getGuests()
                .stream()
                .map(guestInput -> conversionService.convert(guestInput, Guest.class))
                .toList();

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

        List<GuestOutput> guestReports = guests.stream()
                .map(guest -> conversionService.convert(guest, GuestOutput.class))
                .toList();

        GetGuestReportOutput output = GetGuestReportOutput.builder()
                .guestsReports(guestReports)
                .build();

        log.info("End getVisitorsReport {}", output);
        return output;
    }

    @Override
    @Transactional
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        validateCreateRoom(input);

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(input.beds());

        Room room = conversionService.convert(input, Room.class);
        room.setBeds(roomBeds);

        roomRepository.save(room);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .roomId(room.getId().toString())
                .build();

        log.info("End createRoom {}", output);
        return output;
    }

    @Override
    @Transactional
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        validateUpdateRoom(input, room);

        List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(input.getBeds());

        Room updatedRoom = conversionService.convert(input, Room.class);
        updatedRoom.setBeds(roomBeds);
        updatedRoom.setBookings(room.getBookings());

        roomRepository.save(updatedRoom);

        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .roomId(updatedRoom.getId())
                .build();

        log.info("End updateRoom {}", output);
        return output;
    }

    @Override
    @Transactional
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) throws JsonPatchException, JsonProcessingException {
        log.info("Start partialUpdateRoom {}", input);

        Room room = roomRepository.findById(input.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", input.getRoomId().toString()));

        validatePartialUpdate(input, room);

        Room patchedRoom = applyPartialUpdate(input, room);
        patchedRoom.setId(room.getId());

        updateRoomBeds(input, patchedRoom);

        roomRepository.save(patchedRoom);

        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();

        log.info("End partialUpdateRoom {}", output);
        return output;
    }

    @Override
    @Transactional
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

    private void validateCreateRoom(CreateRoomInput input) {
        log.info("Start validateCreateRoom {}", input);

        Long existingRoomNoRooms = roomRepository.countAllByRoomNo(input.roomNo());

        if (existingRoomNoRooms > 0) {
            throw new RoomNoAlreadyExistsException(input.roomNo());
        }

        log.info("End validateCreateRoom {}", existingRoomNoRooms);
    }

    private void validateUpdateRoom(UpdateRoomInput input, Room room) {
        log.info("Start validateUpdateRoom {}", input);
        
        Long existingRoomNoRooms = roomRepository.countAllByRoomNo(input.getRoomNo());
        if (existingRoomNoRooms > 0 &&  !room.getRoomNo().equals(input.getRoomNo())) {
            throw new RoomNoAlreadyExistsException(input.getRoomNo());
        }

        log.info("End validateUpdateRoom {}", existingRoomNoRooms);
    }

    private void validatePartialUpdate(PartialUpdateRoomInput input, Room room) {
        log.info("Start validatePartialUpdate {}", input);
        Long existingRoomNoCount = roomRepository.countAllByRoomNo(input.getRoomNo());

        if (input.getRoomNo() != null) {
            if ( existingRoomNoCount> 0 && !room.getRoomNo().equals(input.getRoomNo())) {
                throw new RoomNoAlreadyExistsException(input.getRoomNo());
            }
        }

        log.info("End validatePartialUpdate {}", input);
    }

    private void updateRoomBeds(PartialUpdateRoomInput input, Room patchedRoom) {
        log.info("Start updateRoomBeds {}", input);
        if (input.getBeds() != null) {
            List<Bed> roomBeds = bedRepository.findAllByBedSizeIn(input.getBeds());
            patchedRoom.setBeds(roomBeds);
        }
        log.info("End updateRoomBeds {}", patchedRoom);
    }

    private Room applyPartialUpdate(PartialUpdateRoomInput input, Room room) throws JsonPatchException, JsonProcessingException {
        log.info("Start applyPartialUpdate {}", input);
        JsonNode roomNode = objectMapper.convertValue(room, JsonNode.class);

        PartialRoomUpdate partialRoomUpdate = conversionService.convert(input, PartialRoomUpdate.class);
        JsonNode inputNode = objectMapper.convertValue(partialRoomUpdate, JsonNode.class);

        JsonMergePatch mergePatch = JsonMergePatch.fromJson(inputNode);

        JsonNode patchedRoomNode = mergePatch.apply(roomNode);

        Room patchedRoom = objectMapper.treeToValue(patchedRoomNode, Room.class);
        log.info("End applyPartialUpdate {}", patchedRoom);
        return patchedRoom;
    }
}
