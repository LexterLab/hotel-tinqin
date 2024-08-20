package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GuestOutput;
import com.tinqinacademy.hotel.api.operations.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRespondWithCreatedAndEmptyBodyWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Petar")
                .lastName("Donatello")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsFirstNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("P")
                .lastName("Donatello")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("PrankensteiPrankensteiPrankensteiPrankensteiPrankensteiPrankensteiPrankensteiPrankenstei")
                .lastName("Donatello")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankFirstNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName(" ")
                .lastName("Donatello")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("DonatelloDonatelloDonatelloDonatelloDonatelloDonatelloDonatelloDonatello")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsLastNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("D")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankLastNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName(" ")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedInvalidBirthdayWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Garfield")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().plusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidedNullBirthdayWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Garfield")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(null)
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankIdCardNoWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo(" ")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardValidityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().minusDays(200))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardValidityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(null)
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinIdCardIssueAuthorityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("B")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxIdCardIssueAuthorityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBGBG")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankIdCardIssueAuthorityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority(" ")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardIssueDateWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().plusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidBookingIdWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("123")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithNotFoundWhenRegisteringGuestToUnknownBooking() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a752")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRespondWithBadRequestWhenRegisteringAlreadyRegisteredGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 3232 3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority("BGN")
                .birthday(LocalDate.now().minusYears(20))
                .build();

        RegisterGuestInput input = RegisterGuestInput.builder()
                .guests(List.of(guestInput))
                .bookingId("4e754a8c-1cca-4abb-b49a-4a07fc98a751")
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithOkAndVisitorReportsWhenRetrievingVisitorReports() throws Exception {
        GuestOutput output = GuestOutput.builder()
                .firstName("Michael")
                .lastName("Jordan")
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_VISITORS_REPORT)
                .param("firstName", output.getFirstName())
                .param("lastName", output.getLastName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.guestsReports").exists())
                .andExpect(jsonPath("$.guestsReports[0].firstName").value(output.getFirstName()))
                .andExpect(jsonPath("$.guestsReports[0].lastName").value(output.getLastName()));
    }

    @Test
    void shouldRespondWithCreatedAndRoomIdWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("301A")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomId").isString());
    }



    @Test
    void shouldRespondWithBadRequestWhenProvidingNullBathRoomTypeWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(null)
                .floor(2)
                .roomNo("401A")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxFloorWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(11)
                .roomNo("401A")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinFloorWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(0)
                .roomNo("401A")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFloorWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(null)
                .roomNo("401A")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxRoomNoCharsWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("201AA")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinRoomNoCharsWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("201")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo(" ")
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingNegativePriceWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("401A")
                .price(BigDecimal.valueOf(-1))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullPriceWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("401A")
                .price(null)
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void shouldRespondWithOKAndRoomIdWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.roomId").isString());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxBedCountWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(11)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinBedCountWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(0)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedCountWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(null)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedSizeWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(null)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBathroomTypeWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(null)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxFloorWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(11)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinFloorWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(0)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullFloorWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(null)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo(null)
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyRoomNoWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo(" ")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxRoomNoWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201AA")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinRoomNoWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNegativePriceWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(-1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNULLPriceWhenUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(null)
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithOKAndRoomIdWhenPartialUpdateRoom() throws Exception {
//        PartialUpdateRoomInput input = PartialUpdateRoomInput.
//                builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.roomId").isString());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxBedCountWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(11)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinBedCountWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(0)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedCountWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(null)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedSizeWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(null)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBathroomTypeWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(null)
//                .floor(10)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxFloorWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(11)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinFloorWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(0)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullFloorWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(null)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo(null)
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyRoomNoWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo(" ")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());;
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxRoomNoWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201AA")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinRoomNoWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201")
//                .price(BigDecimal.valueOf(1))
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNegativePriceWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(-1))
//                .build();
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNULLPriceWhenPartialUpdatingRoom() throws Exception {
//        UpdateRoomInput input = UpdateRoomInput.builder()
//                .bedCount(1)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(null)
//                .build();
//
//        mockMvc.perform(put(RestAPIRoutes.PARTIAL_UPDATE_ROOM, 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithEmptyObjectAndOKWhenDeletingRoom() throws Exception {
//        mockMvc.perform(delete(RestAPIRoutes.DELETE_ROOM, 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//
//    }
}