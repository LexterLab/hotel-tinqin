package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.models.input.*;
import com.tinqinacademy.hotel.models.output.*;

public interface SystemService {
    void registerVisitor(RegisterVisitorInput input);
    GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

}
