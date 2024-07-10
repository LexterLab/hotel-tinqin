package com.tinqinacademy.core.services;


import com.tinqinacademy.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.api.operations.getvisitorreport.GetVisitorsReportInput;
import com.tinqinacademy.api.operations.getvisitorreport.GetVisitorsReportOutput;
import com.tinqinacademy.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.api.operations.updateroom.UpdateRoomOutput;

public interface SystemService {
    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
    GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

}
