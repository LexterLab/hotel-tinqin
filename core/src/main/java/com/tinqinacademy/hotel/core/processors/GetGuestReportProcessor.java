package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReport;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GuestOutput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;

import static io.vavr.API.*;

@Service
@Slf4j
public class GetGuestReportProcessor extends BaseProcessor implements GetGuestReport {
    private final GuestRepository guestRepository;

    public GetGuestReportProcessor(ConversionService conversionService, Validator validator, GuestRepository guestRepository) {
        super(conversionService, validator);
        this.guestRepository = guestRepository;
    }


    @Override
    public Either<ErrorOutput, GetGuestReportOutput> process(GetGuestReportInput input) {
        log.info("Start getVisitorsReport {}", input);

       return Try.of(() -> {
            List<GuestOutput> guestReports = searchGuests(input);

            GetGuestReportOutput output = GetGuestReportOutput.builder()
                    .guestsReports(guestReports)
                    .build();

            log.info("End getVisitorsReport {}", output);
            return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       defaultCase(throwable)
               ));

    }

    private List<GuestOutput> searchGuests(GetGuestReportInput input) {
        log.info("Start searchGuests {}", input);

        List<Guest> guests = guestRepository.searchGuest(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getPhoneNo(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo());

        List<GuestOutput> guestReports = guests.stream()
                .map(guest -> conversionService.convert(guest, GuestOutput.class))
                .toList();

        log.info("End searchGuests {}", guestReports);

        return guestReports;
    }
}
