package com.tinqinacademy.hotel.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.visitor.VisitorInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void shouldRespondWithCreatedAndEmptyBodyWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Petar")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                        .visitors(List.of(visitorInput))
                                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidStartDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().minusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Petar")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullStartDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(null)
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Petar")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidEndDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().minusDays(3))
                .firstName("Petar")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingNullEndDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(null)
                .firstName("Petar")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsFirstNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("P")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("PatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatric")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyFirstNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankFirstNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName(" ")
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName(null)
                .lastName("Donatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("DonatelloDonatelloDonatelloDonatelloDonatelloDoDonatellonatelloDonatelloDonatelloDonatello")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsLastNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("D")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyLastNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankLastNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName(" ")
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName(null)
                .phoneNo("+35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyPhoneNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankPhoneNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo(" ")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingNullPhoneNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo(null)
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidFormatPhoneNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("35984238424")
                .idCardNo("3232 3232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyIdCardNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankIdCardNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo(" ")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo(null)
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardValidityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().minusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardValidityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(null)
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("D")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VIVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVVISASA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }


    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority(" ")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority(null)
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardIssueDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().plusDays(50))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullIdCardIssueDateWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(null)
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201A")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsRoomNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(40))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201AA")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsRoomNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(40))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("201")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingEmptyRoomNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(40))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo("")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(40))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo(" ")
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenRegisteringVisitor() throws Exception {
        VisitorInput visitorInput = VisitorInput.builder()
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .firstName("Rodger")
                .lastName("Federer")
                .phoneNo("+35984238424")
                .idCardNo("32323 323232")
                .idCardIssueDate(LocalDate.now().minusDays(40))
                .idCardValidity(LocalDate.now().plusDays(200))
                .idCardIssueAuthority("VISA")
                .roomNo(null)
                .build();

        RegisterVisitorInput input = RegisterVisitorInput.builder()
                .visitors(List.of(visitorInput))
                .build();

        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isBadRequest());
    }



}