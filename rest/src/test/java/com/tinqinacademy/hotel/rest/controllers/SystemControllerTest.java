//package com.tinqinacademy.hotel.rest.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tinqinacademy.hotel.api.RestAPIRoutes;
//import com.tinqinacademy.hotel.api.models.constants.BathroomType;
//import com.tinqinacademy.hotel.api.models.constants.BedSize;
//import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
//import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
//import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
//import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
//import com.tinqinacademy.hotel.api.operations.visitor.VisitorInput;
//import com.tinqinacademy.hotel.api.operations.visitor.VisitorOutput;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class SystemControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//
//    @Test
//    void shouldRespondWithCreatedAndEmptyBodyWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Petar")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                        .visitors(List.of(visitorInput))
//                                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(input))
//        )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").isEmpty());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidStartDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().minusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Petar")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullStartDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(null)
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Petar")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidEndDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().minusDays(3))
//                .firstName("Petar")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullEndDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(null)
//                .firstName("Petar")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsFirstNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("P")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsFirstNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("PatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatrickPatric")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyFirstNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankFirstNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName(" ")
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullFirstNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName(null)
//                .lastName("Donatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsLastNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("DonatelloDonatelloDonatelloDonatelloDonatelloDoDonatellonatelloDonatelloDonatelloDonatello")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsLastNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("D")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyLastNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankLastNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName(" ")
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullLastNameWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName(null)
//                .phoneNo("+35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyPhoneNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankPhoneNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo(" ")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullPhoneNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo(null)
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidFormatPhoneNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("35984238424")
//                .idCardNo("3232 3232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyIdCardNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankIdCardNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo(" ")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullIdCardNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo(null)
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardValidityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().minusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullIdCardValidityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(null)
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("D")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VIVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVISAVVISASA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority(" ")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullIdCardIssueAuthorityWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority(null)
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingInvalidIdCardIssueDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().plusDays(50))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullIdCardIssueDateWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(null)
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201A")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxCharsRoomNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(40))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201AA")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinCharsRoomNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(40))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("201")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyRoomNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(40))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo("")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(40))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo(" ")
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenRegisteringVisitor() throws Exception {
//        VisitorInput visitorInput = VisitorInput.builder()
//                .startDate(LocalDate.now().plusDays(2))
//                .endDate(LocalDate.now().plusDays(3))
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .idCardNo("32323 323232")
//                .idCardIssueDate(LocalDate.now().minusDays(40))
//                .idCardValidity(LocalDate.now().plusDays(200))
//                .idCardIssueAuthority("VISA")
//                .roomNo(null)
//                .build();
//
//        RegisterVisitorInput input = RegisterVisitorInput.builder()
//                .visitors(List.of(visitorInput))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.REGISTER_VISITOR)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input))
//                )
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithOkAndVisitorReportsWhenRetrievingVisitorReports() throws Exception {
//        VisitorOutput output = VisitorOutput.builder()
//                .firstName("Rodger")
//                .lastName("Federer")
//                .phoneNo("+35984238424")
//                .build();
//
//        mockMvc.perform(get(RestAPIRoutes.GET_VISITORS_REPORT)
//                .param("firstName", "Rodger")
//                .param("lastName", "Federer")
//                .param("phoneNo", "+35984238424")
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.visitorReports").exists())
//                .andExpect(jsonPath("$.visitorReports[0].firstName").value(output.getFirstName()))
//                .andExpect(jsonPath("$.visitorReports[0].lastName").value(output.getLastName()))
//                .andExpect(jsonPath("$.visitorReports[0].phoneNo").value(output.getPhoneNo()));
//    }
//
//    @Test
//    void shouldRespondWithCreatedAndRoomIdWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(2)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.roomId").isString());
//
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxBedCountWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(11)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinBedCountWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(0)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedCountWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(null)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBedSizeWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(null)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//
//    }
//
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullBathRoomTypeWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(null)
//                .floor(2)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxFloorWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(11)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinFloorWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(0)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullFloorWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(null)
//                .roomNo("201A")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingAboveMaxRoomNoCharsWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201AA")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBelowMinRoomNoCharsWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("201")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingEmptyRoomNoWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingBlankRoomNoWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo(" ")
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullRoomNoWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo(null)
//                .price(BigDecimal.valueOf(20))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNegativePriceWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo("")
//                .price(BigDecimal.valueOf(-1))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void shouldRespondWithBadRequestWhenProvidingNullPriceWhenCreatingRoom() throws Exception {
//        CreateRoomInput input = CreateRoomInput.builder()
//                .bedCount(10)
//                .bedSize(BedSize.KING_SIZE)
//                .bathroomType(BathroomType.PRIVATE)
//                .floor(10)
//                .roomNo(null)
//                .price(BigDecimal.valueOf(-1))
//                .build();
//
//        mockMvc.perform(post(RestAPIRoutes.CREATE_ROOM)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(input)))
//                .andExpect(status().isBadRequest());
//    }
//
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
//}