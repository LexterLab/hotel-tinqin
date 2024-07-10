package com.tinqinacademy.api.operations.getvisitorreport;

import com.tinqinacademy.api.operations.visitor.VisitorOutput;
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
