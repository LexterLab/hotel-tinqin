package com.tinqinacademy.hotel.core.processors;

import com.tinqinacademy.hotel.api.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.getguestreport.*;
import com.tinqinacademy.hotel.persistence.models.booking.Booking;
import com.tinqinacademy.hotel.persistence.repositories.BookingRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class GetGuestReportProcessor extends BaseProcessor implements GetGuestReport {
    private final BookingRepository bookingRepository;

    public GetGuestReportProcessor(ConversionService conversionService, Validator validator, BookingRepository bookingRepository) {
        super(conversionService, validator);
        this.bookingRepository = bookingRepository;
    }


    @Override
    public Either<ErrorOutput, GetGuestReportOutput> process(GetGuestReportInput input) {
        log.info("Start getVisitorsReport {}", input);

       return Try.of(() -> {
            GetGuestReportOutput output = searchGuests(input);

            log.info("End getVisitorsReport {}", output);
            return output;
        }).toEither()
               .mapLeft(throwable -> Match(throwable).of(
                       defaultCase(throwable)
               ));

    }

    private GetGuestReportOutput searchGuests(GetGuestReportInput input) {
        log.info("Start searchGuests {}", input);

        List<Booking> bookings = bookingRepository.searchBookings(input.getStartDate(), input.getEndDate(),
                input.getFirstName(), input.getLastName(), input.getIdCardNo(),
                input.getIdCardValidity(), input.getIdCardIssueAuthority(), input.getIdCardIssueDate(),
                input.getRoomNo(), input.getUserId() == null ? null : UUID.fromString(input.getUserId()));

        List<BookingInfo> bookingsInfo = bookings.stream()
                .map(booking -> conversionService.convert(booking, BookingInfo.class))
                .toList();

        BookingReport bookingReport = BookingReport
                .builder()
                .bookings(bookingsInfo)
                .build();

       GetGuestReportOutput guestReports = conversionService.convert(bookingReport, GetGuestReportOutput.class);

        log.info("End searchGuests {}", guestReports);

        return guestReports;
    }
}
