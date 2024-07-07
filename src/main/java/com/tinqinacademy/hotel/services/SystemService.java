package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.operations.getvisitorreport.GetVisitorsReportInput;
import com.tinqinacademy.hotel.operations.getvisitorreport.GetVisitorsReportOutput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.operations.updateroom.UpdateRoomOutput;

public interface SystemService {
    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
    GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

}
