package com.tinqinacademy.hotel.core.configs;

import com.tinqinacademy.hotel.core.converters.CreateRoomInputToRoom;
import com.tinqinacademy.hotel.core.converters.GuestInputToGuest;
import com.tinqinacademy.hotel.core.converters.UpdateRoomInputToRoom;
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

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(createRoomInputToRoom);
        registry.addConverter(updateRoomInputToRoom);
        registry.addConverter(guestInputToGuest);
    }
}
