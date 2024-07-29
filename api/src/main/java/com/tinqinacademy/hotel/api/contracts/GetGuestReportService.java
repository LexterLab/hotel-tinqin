package com.tinqinacademy.hotel.api.contracts;

import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;

public interface GetGuestReportService {
    GetGuestReportOutput getGuestReport(GetGuestReportInput input);
}
