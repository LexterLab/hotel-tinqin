//package com.tinqinacademy.hotel.core.processors;
//
//import com.tinqinacademy.hotel.api.operations.getroom.GetRoomInput;
//import com.tinqinacademy.hotel.persistence.enumerations.BedSize;
//import com.tinqinacademy.hotel.persistence.models.bed.Bed;
//import com.tinqinacademy.hotel.persistence.models.booking.Booking;
//import com.tinqinacademy.hotel.persistence.models.room.Room;
//import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
//import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
//import jakarta.validation.Validator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@ExtendWith(MockitoExtension.class)
//class GetRoomProcessorTest {
//
//    @InjectMocks
//    private GetRoomProcessor getRoomProcessor;
//
//    @Mock
//    private Validator validator;
//
//    @Mock
//    private RoomRepository roomRepository;
//
//    @Mock
//    private BookingRepository bookingRepository;
//
//    @Test
//    void shouldGetRoom() {
//        GetRoomInput input = GetRoomInput
//                .builder()
//                .roomId(UUID.randomUUID().toString())
//                .build();
//
//        Bed bed = Bed
//                .builder()
//                .bedSize(BedSize.KING_SIZE)
//                .bedCapacity(20)
//                .build();
//
//        Room room = Room
//                .builder()
//                .roomNo("201A")
//                .id(UUID.fromString(input.getRoomId()))
//                .price(BigDecimal.valueOf(20))
//                .beds(List.of(bed))
//                .build();
//
//        Booking booking = Booking.builder()
//
//                .build();
//
//        List<Booking> bookings = new ArrayList<>(List.of(booking));
//
//
//    }
//
//
//}