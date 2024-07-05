package com.tinqinacademy.hotel.services;

import com.tinqinacademy.hotel.models.input.CreateRoomInput;
import com.tinqinacademy.hotel.models.input.GetVisitorsReportInput;
import com.tinqinacademy.hotel.models.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.models.output.CreateRoomOutput;
import com.tinqinacademy.hotel.models.output.GetVisitorsReportOutput;

public interface SystemService {
    void registerVisitor(RegisterVisitorInput input);
    GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input);
    CreateRoomOutput createRoom(CreateRoomInput input);
}
