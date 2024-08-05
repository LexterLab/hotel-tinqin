package com.tinqinacademy.hotel.core.configs;

import com.tinqinacademy.hotel.core.converters.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final CreateRoomInputToRoom createRoomInputToRoom;
    private final UpdateRoomInputToRoom updateRoomInputToRoom;
    private final GuestInputToGuest guestInputToGuest;
    private final RoomToGetRoomOutput roomToGetRoomOutput;
    private final BookingToGetGuestReportOutput bookingToGetGuestReportOutput;
    private final PartialUpdateRoomInputToPartialRoomUpdate partialUpdateRoomInputToPartialRoomUpdate;
    private final BookingToBookingInfo bookingToBookingInfo;
    private final RoomToPartialRoomUpdate roomToPartialRoomUpdate;


    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(createRoomInputToRoom);
        registry.addConverter(updateRoomInputToRoom);
        registry.addConverter(guestInputToGuest);
        registry.addConverter(roomToGetRoomOutput);
        registry.addConverter(bookingToGetGuestReportOutput);
        registry.addConverter(partialUpdateRoomInputToPartialRoomUpdate);
        registry.addConverter(bookingToBookingInfo);
        registry.addConverter(roomToPartialRoomUpdate);
    }
}
