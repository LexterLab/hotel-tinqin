package com.tinqinacademy.hotel.services.impl;

import com.tinqinacademy.hotel.models.input.CreateRoomInput;
import com.tinqinacademy.hotel.models.input.GetVisitorsReportInput;
import com.tinqinacademy.hotel.models.input.RegisterVisitorInput;
import com.tinqinacademy.hotel.models.mappers.VisitorReportMapper;
import com.tinqinacademy.hotel.models.output.CreateRoomOutput;
import com.tinqinacademy.hotel.models.output.GetVisitorsReportOutput;
import com.tinqinacademy.hotel.models.output.VisitorOutput;
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
}
