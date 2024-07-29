package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.exceptions.GuestAlreadyRegisteredException;
import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestInput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SystemServiceImplTest {

    @InjectMocks
    private SystemServiceImpl systemService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BedRepository bedRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ConversionService conversionService;



    @Test
    void shouldRegisterGuestAndRespondWithEmptyObject() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");


        GuestInput guestInput = GuestInput
                .builder()
                .firstName("Alex")
                .lastName("Peter")
                .birthday(LocalDate.now().minusYears(18))
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .build();

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .guests(List.of(guestInput))
                .build();

        Guest guest = Guest
                .builder()
                .idCardNo(guestInput.getIdCardNo())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        Booking booking = Booking.builder()
                .guests(guests)
                .id(bookingId)
                .build();


        when(bookingRepository.findById(input.getBookingId())).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.empty());

        systemService.registerGuest(input);

        verify(bookingRepository).save(any(Booking.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenBookingNotFoundWhenRegisteringGuestForNonExistingBooking() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .build();

        when(bookingRepository.findById(input.getBookingId())).thenThrow(ResourceNotFoundException.class);


       assertThrows(ResourceNotFoundException.class, () -> systemService.registerGuest(input));
    }

    @Test
    void shouldThrowGuestAlreadyRegisteredExceptionWhenRegisteringAlreadyRegisteredGuest() {
        UUID bookingId = UUID.fromString("7981aebc-10d9-4202-b129-53b7ec80e06e");


        GuestInput guestInput = GuestInput
                .builder()
                .firstName("Alex")
                .lastName("Peter")
                .birthday(LocalDate.now().minusYears(18))
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .build();

        RegisterGuestInput input = RegisterGuestInput
                .builder()
                .bookingId(bookingId)
                .guests(List.of(guestInput))
                .build();

        Guest guest = Guest
                .builder()
                .id(UUID.randomUUID())
                .idCardNo(guestInput.getIdCardNo())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        Booking booking = Booking.builder()
                .guests(guests)
                .id(bookingId)
                .build();


        when(bookingRepository.findById(input.getBookingId())).thenReturn(Optional.of(booking));
        when(conversionService.convert(guestInput, Guest.class)).thenReturn(guest);
        when(guestRepository.findByIdCardNo(guestInput.getIdCardNo())).thenReturn(Optional.of(guest));

        assertThrows(GuestAlreadyRegisteredException.class, () -> systemService.registerGuest(input));
    }


    @Test
    void shouldReturnGuestReports() {
        GetGuestReportInput input = GetGuestReportInput
                .builder()
                .idCardNo("232 3 3232 32")
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BULGARIA")
                .idCardIssueDate(LocalDate.now().minusYears(2))
                .firstName("Alex")
                .lastName("Peter")
                .startDate(LocalDateTime.now().plusYears(1))
                .endDate(LocalDateTime.now().plusYears(1))
                .phoneNo("+3598535353453")
                .build();


        Guest guest  = Guest
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();

        List<Guest> guests = new ArrayList<>(List.of(guest));

        GuestOutput guestOutput = GuestOutput
                .builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .build();

        GetGuestReportOutput expectedOutput = GetGuestReportOutput
                .builder()
                .guestsReports(List.of(guestOutput))
                .build();

        when(guestRepository.searchGuest(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getPhoneNo(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo())).thenReturn(guests);
        when(conversionService.convert(guest, GuestOutput.class)).thenReturn(guestOutput);

        GetGuestReportOutput output = systemService.getGuestReport(input);

        assertEquals(expectedOutput.toString(), output.toString());
    }

    @Test
    void shouldCreateRoom() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        List<com.tinqinacademy.hotel.persistence.enumerations.BedSize> bedSizes = input.beds().stream()
                .map(bedSize -> com.tinqinacademy.hotel.persistence.enumerations.BedSize
                        .getByCode(bedSize.toString()))
                .toList();

        Bed bed = Bed
                .builder()
                .bedCapacity(2)
                .bedSize(com.tinqinacademy.hotel.persistence.enumerations.BedSize.KING_SIZE)
                .build();

        List<Bed> roomBeds = List.of(bed);

        Room room = Room.builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.PRIVATE)
                .beds(roomBeds)
                .price(input.price())
                .floor(input.floor())
                .roomNo(input.roomNo())
                .id(UUID.randomUUID())
                .build();

        when(roomRepository.countAllByRoomNo(input.roomNo())).thenReturn(0L);
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);

        CreateRoomOutput output = systemService.createRoom(input);

        assertEquals(room.getId().toString(), output.getRoomId());
    }

    @Test
    void shouldThrowRoomNoAlreadyExistsExceptionWhenCreatingRoomWithExistingRoomNo() {
        CreateRoomInput input = CreateRoomInput
                .builder()
                .roomNo("201A")
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .price(BigDecimal.valueOf(200))

                .bathroomType(BathroomType.PRIVATE)
                .build();



        when(roomRepository.countAllByRoomNo(input.roomNo())).thenReturn(1L);


       assertThrows(RoomNoAlreadyExistsException.class, () -> systemService.createRoom(input));
    }

    @Test
    void shouldUpdateRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        Bed bed = Bed
                .builder()
                .bedSize(com.tinqinacademy.hotel.persistence.enumerations.BedSize.KING_SIZE)
                .bedCapacity(20)
                .build();

        List<Bed> roomBeds = List.of(bed);

        List<com.tinqinacademy.hotel.persistence.enumerations.BedSize> bedSizes = input.getBeds().stream()
                .map(bedSize -> com.tinqinacademy.hotel.persistence.enumerations.BedSize.getByCode(bedSize.toString()))
                .toList();

        Room room = Room
                .builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.SHARED)
                .roomNo("201A")
                .floor(4)
                .beds(List.of())
                .price(BigDecimal.valueOf(200))
                .build();

        Room updatedRoom = Room
                .builder()
                .id(input.getRoomId())
                .roomNo(input.getRoomNo())
                .floor(input.getFloor())
                .beds(roomBeds)
                .price(input.getPrice())
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.getByCode(input.getBathroomType().toString()))
                .build();


        when(roomRepository.findById(input.getRoomId())).thenReturn(Optional.of(room));
        when(bedRepository.findAllByBedSizeIn(bedSizes)).thenReturn(roomBeds);
        when(conversionService.convert(input, Room.class)).thenReturn(updatedRoom);
        when(roomRepository.save(updatedRoom)).thenReturn(updatedRoom);

        UpdateRoomOutput output = systemService.updateRoom(input);

        assertEquals(updatedRoom.getId(), output.getRoomId());
    }

    @Test
    void shouldThrowResourceNotFoundWhenUpdatingUnexistingRoom() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        when(roomRepository.findById(input.getRoomId())).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> systemService.updateRoom(input));
    }

    @Test
    void shouldThrowRoomNoAlreadyExistsExceptionWhenUpdatingRoomWithExistingRoomNo() {
        UpdateRoomInput input = UpdateRoomInput
                .builder()
                .roomNo("202A")
                .roomId(UUID.randomUUID())
                .floor(4)
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .build();

        Room room = Room
                .builder()
                .bathroomType(com.tinqinacademy.hotel.persistence.enumerations.BathroomType.SHARED)
                .roomNo("201A")
                .floor(4)
                .beds(List.of())
                .price(BigDecimal.valueOf(200))
                .build();

        when(roomRepository.findById(input.getRoomId())).thenReturn(Optional.of(room));
        when(roomRepository.countAllByRoomNo(input.getRoomNo())).thenReturn(1L);

        assertThrows(RoomNoAlreadyExistsException.class, () -> systemService.updateRoom(input));
    }

}