package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.exceptions.ResourceNotFoundException;
import com.tinqinacademy.hotel.api.models.constants.BedSize;
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
import com.tinqinacademy.hotel.api.operations.visitor.VisitorOutput;
import com.tinqinacademy.hotel.api.contracts.SystemService;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import com.tinqinacademy.hotel.persistence.models.room.Room;

import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
import com.tinqinacademy.hotel.persistence.repositories.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SystemServiceImpl implements SystemService {
    private final RoomRepository roomRepository;
    private final BedRepository bedRepository;

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
    @Transactional
    public CreateRoomOutput createRoom(CreateRoomInput input) {
        log.info("Start createRoom {}", input);

        List<Bed> roomBeds = new ArrayList<>();

        for (BedSize size : input.beds()) {
            Bed bed = bedRepository.findByBedSize(size)
                    .orElseThrow(() -> new ResourceNotFoundException("Bed", "bedSize", size.toString()));
            roomBeds.add(bed);
        }

        Room room = Room.builder()
                .id(UUID.randomUUID())
                .roomNo(input.roomNo())
                .floor(input.floor())
                .price(input.price())
                .bathroomType(input.bathroomType())
                .beds(roomBeds)
                .build();



        roomRepository.save(room);
        roomRepository.saveRoomBeds(roomBeds, room);

        CreateRoomOutput output = CreateRoomOutput.builder()
                .roomId(room.getId().toString())
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
