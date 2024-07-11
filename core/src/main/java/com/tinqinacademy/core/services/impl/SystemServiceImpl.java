package com.tinqinacademy.core.services.impl;


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
import com.tinqinacademy.api.operations.visitor.VisitorOutput;
import com.tinqinacademy.api.contracts.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Override
    public RegisterVisitorOutput registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor {}", input);
        RegisterVisitorOutput output = RegisterVisitorOutput.builder().build();
        log.info("End registerVisitor {}", output);
        return output;
    }

    @Override
    public GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input) {
        log.info("Start getVisitorsReport {}", input);

        VisitorOutput matchingVisitor = VisitorOutput.builder()
                .endDate(input.getEndDate())
                .startDate(input.getStartDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .idCardNo(input.getIdCardNo())
                .idCardValidity(input.getIdCardValidity())
                .phoneNo(input.getPhoneNo())
                .roomNo(input.getRoomNo())
                .build();

        List<VisitorOutput> matchingVisitors = List.of(matchingVisitor);

        GetVisitorsReportOutput output = GetVisitorsReportOutput.builder()
                .visitorReports(matchingVisitors)
                .build();

        log.info("End getVisitorsReport {}", output);
        return output;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        CreateRoomOutput output = CreateRoomOutput.builder().
                roomId(input.roomNo())
                .build();
        log.info("End createRoom {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);
        UpdateRoomOutput output = UpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();
        log.info("End updateRoom {}", output);
        return output;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom {}", input);
        PartialUpdateRoomOutput output = PartialUpdateRoomOutput.builder()
                .roomId(input.getRoomId())
                .build();
        log.info("End partialUpdateRoom {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);
        DeleteRoomOutput output = DeleteRoomOutput.builder().build();
        log.info("End deleteRoom {}", output);
        return output;
    }


}
