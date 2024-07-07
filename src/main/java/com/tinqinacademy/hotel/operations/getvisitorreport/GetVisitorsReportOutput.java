package com.tinqinacademy.hotel.operations.getvisitorreport;

import com.tinqinacademy.hotel.operations.visitor.VisitorOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class GetVisitorsReportOutput {
    List<VisitorOutput> visitorReports;
}
