package com.tinqinacademy.hotel.models.output;

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
