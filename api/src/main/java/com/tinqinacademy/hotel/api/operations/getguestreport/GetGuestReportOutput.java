package com.tinqinacademy.hotel.api.operations.getguestreport;

import com.tinqinacademy.hotel.api.base.OperationOutput;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class GetGuestReportOutput implements OperationOutput {
    List<GuestOutput> guestsReports;
}
