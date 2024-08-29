package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.bookroom.BookRoomInput;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import com.tinqinacademy.hotel.api.operations.unbookroom.UnbookRoomInput;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class HotelControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void shouldRespondWithOkAndAvailableRoomsWhenSearchingAvailableRooms() throws Exception {

        mockMvc.perform(get(RestAPIRoutes.SEARCH_ROOMS)
                        .param("startDate", String.valueOf(LocalDateTime.now()))
                        .param("endDate", String.valueOf(LocalDateTime.now())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomIds").isNotEmpty())
                .andExpect(jsonPath("$.roomIds").isArray());
    }

    @Test
    void shouldRespondWithOKAndRoomDataWhenGettingRoomById() throws Exception {
        GetRoomOutput output = GetRoomOutput.builder()
                .id(UUID.fromString("923364b0-4ed0-4a7e-8c23-ceb5c238ceee"))
                .price(BigDecimal.valueOf(20000.00))
                .floor(4)
                .bedSizes(List.of(BedSize.SINGLE))
                .bathroomType(BathroomType.PRIVATE)
                .bedCount(1)
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_DETAILS, output.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.getId().toString()))
                .andExpect(jsonPath("$.price").value(output.getPrice()))
                .andExpect(jsonPath("$.floor").value(output.getFloor()))
                .andExpect(jsonPath("$.bedSizes[0]").value(output.getBedSizes().getFirst().toString()))
                .andExpect(jsonPath("$.bathroomType").value(output.getBathroomType().toString()));
    }

    @Test
    void shouldRespondWithBadRequestWhenGettingRoomByInvalidId() throws Exception {
        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_DETAILS, "invalidId"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomId must be UUID"));
    }

    @Test
    void shouldRespondWithNotFoundWhenGettingRoomByUnknownId() throws Exception {
        String unknownId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceea";
        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_DETAILS, unknownId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.RESOURCE_NOT_FOUND, "room", "id", unknownId)));
    }

    @Test
    void shouldRespondWithCREATEDAndEmptyBodyWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(LocalDateTime.now().plusMonths(1).plusWeeks(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        long numberOfBookingBefore = bookingRepository.count();

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isEmpty());

        long numberOfBookingAfter = bookingRepository.count();

        Assertions.assertTrue(numberOfBookingAfter > numberOfBookingBefore);
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidStartDateWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().minusDays(2))
                .endDate(LocalDateTime.now().plusMonths(1).plusWeeks(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field startDate must be future or present"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullStartDateWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(null)
                .endDate(LocalDateTime.now().plusMonths(1).plusWeeks(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field startDate should not be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidEndDateWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(LocalDateTime.now().minusDays(2))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field endDate must be future or present"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullEndDateWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(null)
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field endDate should not be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidRoomIdWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(LocalDateTime.now().plusMonths(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "invalid";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomId must be UUID"));
    }

    @Test
    void shouldRespondWithNotFoundWhenProvidingUnknownRoomIdWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(LocalDateTime.now().plusMonths(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = UUID.randomUUID().toString();

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.RESOURCE_NOT_FOUND, "Room", "Id", roomId )));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidUserIdWhenBookingRoom() throws Exception {
        BookRoomInput input = BookRoomInput.builder()
                .startDate(LocalDateTime.now().plusMonths(1))
                .endDate(LocalDateTime.now().plusMonths(1))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("invalid")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field userId must be UUID"));
    }

    @Test
    void shouldRespondWithBadRequestWhenBookingUnAvailableRoom() throws Exception {
        LocalDateTime unavailableDate = LocalDateTime.of(2025, 8, 13, 16, 7, 24, 284000);

        BookRoomInput input = BookRoomInput.builder()
                .startDate(unavailableDate)
                .endDate(LocalDateTime.now().plusYears(2))
                .firstName("George")
                .lastName("Russell")
                .phoneNo("+3598312313")
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value(Messages.DATE_NOT_AVAILABLE));
    }

    @Test
    void shouldRespondWithOKAndEmptyBodyWhenUnbookingRoom() throws Exception {
        String bookingId = "4e754a8c-1cca-4abb-b49a-4a07fc98a751";

        UnbookRoomInput input = UnbookRoomInput
                .builder()
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        mockMvc.perform(delete(RestAPIRoutes.UNBOOK_ROOM, bookingId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        Optional<Booking> booking = bookingRepository.findById(UUID.fromString(bookingId));

        Assertions.assertTrue(booking.isEmpty());
    }

    @Test
    void shouldRespondWithBadRequestWhenUnbookingRoomWithInvalidBookingId() throws Exception {
        String bookingId = "invalid";

        UnbookRoomInput input = UnbookRoomInput
                .builder()
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        mockMvc.perform(delete(RestAPIRoutes.UNBOOK_ROOM, bookingId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field bookingId must be UUID"));
    }

    @Test
    void shouldRespondWithBadRequestWhenUnbookingRoomWithInvalidUserId() throws Exception {
        String bookingId = "4e754a8c-1cca-4abb-b49a-4a07fc98a751";

        UnbookRoomInput input = UnbookRoomInput
                .builder()
                .userId("invalid")
                .build();

        mockMvc.perform(delete(RestAPIRoutes.UNBOOK_ROOM, bookingId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field userId must be UUID"));
    }

    @Test
    void shouldRespondWithNotFoudWhenUnbookingRoomWithUnknownBookingId() throws Exception {
        String bookingId = UUID.randomUUID().toString();

        UnbookRoomInput input = UnbookRoomInput
                .builder()
                .userId("8eabb4ff-df5b-4e39-8642-0dcce375798c")
                .build();

        mockMvc.perform(delete(RestAPIRoutes.UNBOOK_ROOM, bookingId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.RESOURCE_NOT_FOUND, "booking", "bookingId & userId", bookingId + " - " + input.getUserId())));
    }

    @Test
    void shouldRespondWithOKAndRoomInfoWhenFindingRoomByRoomNo() throws Exception {
        String roomNo =  "201A";
        String id = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(get(RestAPIRoutes.FIND_ROOM, roomNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNo").value(roomNo))
                .andExpect(jsonPath("$.id").value(id));
    }

}