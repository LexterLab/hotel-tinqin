package com.tinqinacademy.hotel.core.services;

import com.tinqinacademy.hotel.api.contracts.AuthenticationService;
import com.tinqinacademy.hotel.api.exceptions.EmailAlreadyExistsException;
import com.tinqinacademy.hotel.api.operations.signup.SignUpInput;
import com.tinqinacademy.hotel.api.operations.signup.SignUpOutput;
import com.tinqinacademy.hotel.core.mappers.UserMapper;
import com.tinqinacademy.hotel.persistence.models.user.User;
import com.tinqinacademy.hotel.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    @Override
    public SignUpOutput signUp(SignUpInput input) {
        log.info("Start signUp: {}", input);
        User user = UserMapper.INSTANCE.SignUpInputToUser(input);


        if (userRepository.countByEmail(user.getEmail()) > 0) {
            throw new EmailAlreadyExistsException();
        }

        userRepository.save(user);

        SignUpOutput output = SignUpOutput
                .builder()
                .id(user.getId())
                .build();

        log.info("End signUp: {}", output);
        return output;
    }
}
