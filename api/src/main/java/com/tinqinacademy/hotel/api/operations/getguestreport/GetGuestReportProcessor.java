package com.tinqinacademy.hotel.api.operations.getguestreport;

import com.tinqinacademy.hotel.api.base.OperationProcessor;

public interface GetGuestReportProcessor extends OperationProcessor<GetGuestReportInput, GetGuestReportOutput> {
    GetGuestReportOutput process(GetGuestReportInput input);
}
