package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithNotFoundWhenGettingRoomByUnknownId() throws Exception {
        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_DETAILS, "923364b0-4ed0-4a7e-8c23-ceb5c238ceea"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void shouldRespondWithCREATEDAndEmptyBodyWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidStartDateWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now().minusDays(2))
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullStartDateWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(null)
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidEndDateWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now().minusDays(2))
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullEndDateWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(null)
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxFirstNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("GeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorgeGeorge")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinFirstNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("G")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyFirstNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankFirstNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName(" ")
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName(null)
//                .lastName("Russell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxLastNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("RussellRussellRussellRussellRussellRussellRussellRussell")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinLastNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("R")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyLastNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankLastNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName(" ")
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName(null)
//                .phoneNo("+3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidPhoneNoWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("3598312313")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyPhoneNoWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo("")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankPhoneNoWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo(" ")
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullPhoneNoWhenBookingRoom() throws Exception {
//        BookRoomInput input = BookRoomInput.builder()
//                .startDate(LocalDate.now())
//                .endDate(LocalDate.now())
//                .firstName("George")
//                .lastName("Russell")
//                .phoneNo(null)
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.BOOK_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithOKAndEmptyBodyWhenUnbookingRoom() throws Exception {
//        mockMvc.perform(delete(RestAPIRoutes.UNBOOK_ROOM, 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
}