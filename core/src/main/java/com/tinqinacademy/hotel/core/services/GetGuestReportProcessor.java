package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.exceptions.RoomNoAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReport;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportInput;
import com.tinqinacademy.hotel.api.operations.getguestreport.GetGuestReportOutput;
import com.tinqinacademy.hotel.api.operations.guest.GuestOutput;
import com.tinqinacademy.hotel.persistence.models.guest.Guest;
import com.tinqinacademy.hotel.persistence.repositories.GuestRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetGuestReportProcessor implements GetGuestReport {
    private final GuestRepository guestRepository;
    private final ConversionService conversionService;

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
                       Case($(), () -> ErrorOutput.builder()
                               .errors(List.of(Error.builder()
                                       .message(throwable.getMessage())
                                       .build()))
                               .build())
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
