package com.tinqinacademy.hotel.api.contracts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterGuestOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;

public interface SystemService {
    RegisterGuestOutput registerGuest(RegisterGuestInput input);
    GetGuestReportOutput getGuestReport(GetGuestReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input)
            throws JsonPatchException, JsonProcessingException;
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

}

