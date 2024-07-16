package com.tinqinacademy.hotel.rest.controllers;

import com.tinqinacademy.hotel.api.RestAPIRoutes;
import com.tinqinacademy.hotel.api.models.constants.BathroomType;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
import com.tinqinacademy.hotel.api.operations.getroom.GetRoomOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldRespondWithOkAndAvailableRoomsWhenSearchingAvailableRooms() throws Exception {
        List<String> roomIds = List.of("2", "3", "4", "5", "6", "7", "8", "9");

        mockMvc.perform(get(RestAPIRoutes.SEARCH_ROOMS)
                .param("startDate", String.valueOf(LocalDate.now()))
                .param("endDate", String.valueOf(LocalDate.now()))
                        .param("bedCount", String.valueOf(10))
                        .param("bedSize", "single")
                        .param("bathroomType", "shared"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomIds").isNotEmpty())
                .andExpect(jsonPath("$.roomIds").isArray())
                .andExpect(jsonPath("$.roomIds[0]").value(roomIds.getFirst()))
                .andExpect(jsonPath("$.roomIds[1]").value(roomIds.get(1)))
                .andExpect(jsonPath("$.roomIds[2]").value(roomIds.get(2)))
                .andExpect(jsonPath("$.roomIds[3]").value(roomIds.get(3)))
                .andExpect(jsonPath("$.roomIds[4]").value(roomIds.get(4)))
                .andExpect(jsonPath("$.roomIds[5]").value(roomIds.get(5)))
                .andExpect(jsonPath("$.roomIds[6]").value(roomIds.get(6)))
                .andExpect(jsonPath("$.roomIds[7]").value(roomIds.get(7)));
    }

    @Test
    void shouldRespondWithOKAndRoomDataWhenGettingRoomById() throws Exception {
        GetRoomOutput output = GetRoomOutput.builder()
                .id("2")
                .price(BigDecimal.valueOf(23232))
                .floor(4)
                .bedSize(BedSize.KING_SIZE)
                .bathroomType(BathroomType.PRIVATE)
                .datesOccupied(List.of(LocalDate.now()))
                .build();

        mockMvc.perform(get(RestAPIRoutes.GET_ROOM_DETAILS, output.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.getId()))
                .andExpect(jsonPath("$.price").value(output.getPrice()))
                .andExpect(jsonPath("$.floor").value(output.getFloor()))
                .andExpect(jsonPath("$.bedSize").value(output.getBedSize().toString()))
                .andExpect(jsonPath("$.bathroomType").value(output.getBathroomType().toString()))
                .andExpect(jsonPath("$.datesOccupied").value(output.getDatesOccupied().getFirst().toString()));


    }

}