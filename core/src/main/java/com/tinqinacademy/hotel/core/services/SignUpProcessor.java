package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.operations.errors.Error;
import com.tinqinacademy.hotel.api.operations.errors.ErrorOutput;
import com.tinqinacademy.hotel.api.operations.signup.SignUp;
import com.tinqinacademy.hotel.api.exceptions.EmailAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.signup.SignUpInput;
import com.tinqinacademy.hotel.api.operations.signup.SignUpOutput;
import com.tinqinacademy.hotel.core.mappers.UserMapper;
import com.tinqinacademy.hotel.persistence.models.user.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;


@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpProcessor implements SignUp {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Either<ErrorOutput, SignUpOutput> process(SignUpInput input) {
        log.info("Start signUp: {}", input);


        return Try.of(() -> {
            checkForExistingEmail(input);

            User user = UserMapper.INSTANCE.SignUpInputToUser(input);

            userRepository.save(user);

            SignUpOutput output = SignUpOutput
                    .builder()
                    .id(user.getId())
                    .build();

            log.info("End signUp: {}", output);
            return output;
        }).toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(EmailAlreadyExistsException.class)), () -> ErrorOutput.builder()
                                .errors(List.of(Error.builder()
                                        .message(throwable.getMessage())
                                        .build()))
                                .build()),
                        Case($(), () -> ErrorOutput.builder()
                                .errors(List.of(Error.builder()
                                        .message(throwable.getMessage())
                                        .build()))
                                .build())
                ));


    }

    private void checkForExistingEmail(SignUpInput input) {
        log.info("Start checkForExistingEmail: {}", input);

        if (userRepository.countByEmail(input.getEmail()) > 0) {
            throw new EmailAlreadyExistsException();
        }

    }
}
