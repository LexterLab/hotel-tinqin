package com.tinqinacademy.hotel.core.converters;

import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialRoomUpdate;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import org.springframework.stereotype.Component;

@Component
public class PartialUpdateRoomInputToPartialRoomUpdate extends AbstractConverter<PartialUpdateRoomInput, PartialRoomUpdate> {

    @Override
    protected Class<PartialRoomUpdate> getTargetClass() {
        return PartialRoomUpdate.class;
    }

    @Override
    protected PartialRoomUpdate doConvert(PartialUpdateRoomInput source) {
        PartialRoomUpdate partialUpdate = PartialRoomUpdate
                .builder()
                .price(source.getPrice())
                .roomNo(source.getRoomNo())
                .floor(source.getFloor())
                .bathroomType(source.getBathroomType())
                .build();

        return partialUpdate;
    }
}
