package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.Messages;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.enumerations.BathroomType;
import com.tinqinacademy.hotel.api.enumerations.BedSize;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GuestOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.registerguest.GuestInput;
import com.tinqinacademy.hotel.api.operations.registerguest.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.persistence.models.room.Room;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

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

        long guestCountBefore = guestRepository.count();
      
        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR, input.getBookingId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isEmpty());

        long guestCountAfter = guestRepository.count();


        Assertions.assertTrue(guestCountAfter> guestCountBefore);


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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be between 2-20 characters"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must be between 2-20 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName(null)
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field firstName must not be empty"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be between 2-20 characters"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must be between 2-20 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName(null)
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field lastName must not be empty"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field birthDay must be a past date"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field birthDay cannot be null"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field idCardNo must not be empty"));

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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field idCardValidity must be valid"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field idCardValidity should not be null"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Field idCardIssueAuthority must be between 2-100 characters"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Field idCardIssueAuthority must be between 2-100 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardIssueAuthorityWhenRegisteringGuest() throws Exception {
        GuestInput guestInput = GuestInput.builder()
                .firstName("Linus")
                .lastName("Torvald")
                .idCardNo("3232 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusYears(2))
                .idCardIssueAuthority(null)
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field idCardIssueAuthority must not be empty"));

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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field idCardIssueDate must be past or present"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field bookingId must be UUID"));
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
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].message").value(String.format(Messages.RESOURCE_NOT_FOUND, "Booking", "id" , input.getBookingId())));
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
    void shouldRespondWithOkAndVisitorReportsWhenRetrievingGuestReports() throws Exception {
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

        Assertions.assertTrue(roomRepository.findByRoomNo(input.getRoomNo()).isPresent());

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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field bathroomType must not be null"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field floor must be maximum 12"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field floor must be minimum 1"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field floor cannot be null"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must be 4 characters"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must be 4 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenCreatingRoom() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo(null)
                .price(BigDecimal.valueOf(20))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must not be empty"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field price must be min 0"));
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
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field price cannot be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenCreatingRoomWithAlreadyExistingRoomNo() throws Exception {
        CreateRoomInput input = CreateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("201A")
                .price(BigDecimal.valueOf(1))
                .build();

        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.ROOM_NO_ALREADY_EXISTS, input.getRoomNo())));
    }

    @Test
    void shouldRespondWithOKAndRoomIdWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(9)
                .roomNo("301A")
                .price(BigDecimal.valueOf(1.00))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").isString())
                .andExpect(jsonPath("$.roomId").value(roomId));

        Room updatedRoom = roomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new AssertionError("Room not found"));

        Assertions.assertEquals(input.getBathroomType().toString(), updatedRoom.getBathroomType().toString());
        Assertions.assertEquals(input.getRoomNo(), updatedRoom.getRoomNo());
        Assertions.assertEquals(0, input.getPrice().compareTo(updatedRoom.getPrice()));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullBedsWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(null)
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("401A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field beds cannot be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullBathroomTypeWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(null)
                .floor(10)
                .roomNo("201A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field bathroomType must not be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxFloorWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(11)
                .roomNo("401A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value("Field floor must be maximum 10"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinFloorWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(0)
                .roomNo("401A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field floor must be minimum 1"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFloorWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(null)
                .roomNo("401A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field floor cannot be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo(null)
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must not be empty"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxRoomNoWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("201AA")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must be 4 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinRoomNoWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("201")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field roomNo must be 4 characters"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNegativePriceWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("401A")
                .price(BigDecimal.valueOf(-1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field price must be min 0"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNULLPriceWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("401A")
                .price(null)
                .build();
        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value("Field price cannot be null"));
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingExistingRoomNoWhenUpdatingRoom() throws Exception {
        UpdateRoomInput input = UpdateRoomInput.builder()
                .beds(List.of(BedSize.KING_SIZE))
                .bathroomType(BathroomType.PRIVATE)
                .floor(2)
                .roomNo("901A")
                .price(BigDecimal.valueOf(1))
                .build();
        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(put(RestAPIRoutes.UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message").value(String.format(Messages.ROOM_NO_ALREADY_EXISTS, input.getRoomNo())));
    }

    @Test
    void shouldRespondWithOKAndRoomIdWhenPartialUpdateRoom() throws Exception {
        PartialUpdateRoomInput input = PartialUpdateRoomInput.builder()
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("301A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(patch(RestAPIRoutes.PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomId").isString())
                .andExpect(jsonPath("$.roomId").value(roomId));

        Room updatedRoom = roomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new AssertionError("Not found"));

        Assertions.assertEquals(input.getRoomNo(), updatedRoom.getRoomNo());
        Assertions.assertEquals(0, input.getPrice().compareTo(updatedRoom.getPrice()));
        Assertions.assertEquals(input.getBathroomType().toString(), updatedRoom.getBathroomType().toString());
    }

    @Test
    void shouldRespondWithBadRequestWhenPartialUpdateRoomWithExistingRoomNo() throws Exception {
        PartialUpdateRoomInput input = PartialUpdateRoomInput.builder()
                .bathroomType(BathroomType.PRIVATE)
                .floor(10)
                .roomNo("901A")
                .price(BigDecimal.valueOf(1))
                .build();

        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(patch(RestAPIRoutes.PARTIAL_UPDATE_ROOM, roomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].message")
                        .value(String.format(Messages.ROOM_NO_ALREADY_EXISTS, input.getRoomNo())));
    }


    @Test
    void shouldRespondWithEmptyObjectAndOKWhenDeletingRoom() throws Exception {
        String roomId = "923364b0-4ed0-4a7e-8c23-ceb5c238ceee";

        mockMvc.perform(delete(RestAPIRoutes.DELETE_ROOM, roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}