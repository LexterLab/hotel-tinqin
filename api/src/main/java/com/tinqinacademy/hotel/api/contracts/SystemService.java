package com.tinqinacademy.hotel.api.contracts;


import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.createroom.CreateRoomOutput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomInput;
import com.tinqinacademy.hotel.api.operations.deleteroom.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.operations.getvisitorreport.GetVisitorsReportInput;
import com.tinqinacademy.hotel.api.operations.getvisitorreport.GetVisitorsReportOutput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.partialupdateroom.PartialUpdateRoomOutput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.operations.registervisitor.RegisterVisitorOutput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.updateroom.UpdateRoomOutput;

public interface SystemService {
    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
    GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
    UpdateRoomOutput updateRoom(UpdateRoomInput input);
    PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input);
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);

}
