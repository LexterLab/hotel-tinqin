package com.tinqinacademy.hotel.services.impl;

import com.tinqinacademy.hotel.models.input.*;
import com.tinqinacademy.hotel.models.mappers.VisitorReportMapper;
import com.tinqinacademy.hotel.models.output.*;
import com.tinqinacademy.hotel.services.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {
    @Override
    public void registerVisitor(RegisterVisitorInput input) {
        log.info("Start registerVisitor {}", input);
        log.info("End registerVisitor");
    }

    @Override
    public GetVisitorsReportOutput getVisitorsReport(GetVisitorsReportInput input) {
        log.info("Start getVisitorsReport {}", input);

        List<VisitorOutput> matchingVisitors = List.of(VisitorReportMapper.INSTANCE.inputToOutput(input));

        GetVisitorsReportOutput output = GetVisitorsReportOutput.builder()
                .visitorReports(matchingVisitors)
                .build();

        log.info("End getVisitorsReport {}", output);
        return output;
    }

    @Override
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        CreateRoomOutput output = new CreateRoomOutput("dadada3232");
        log.info("End createRoom {}", output);
        return output;
    }

    @Override
    public UpdateRoomOutput updateRoom(UpdateRoomInput input) {
        log.info("Start updateRoom {}", input);
        UpdateRoomOutput output = new UpdateRoomOutput(input.getRoomId());
        log.info("End updateRoom {}", output);
        return output;
    }

    @Override
    public PartialUpdateRoomOutput partialUpdateRoom(PartialUpdateRoomInput input) {
        log.info("Start partialUpdateRoom {}", input);
        PartialUpdateRoomOutput output = new PartialUpdateRoomOutput(input.getRoomId());
        log.info("End partialUpdateRoom {}", output);
        return output;
    }

    @Override
    public DeleteRoomOutput deleteRoom(DeleteRoomInput input) {
        log.info("Start deleteRoom {}", input);
        DeleteRoomOutput output = new DeleteRoomOutput();
        log.info("End deleteRoom {}", output);
        return output;
    }


}
