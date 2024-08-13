package com.tinqinacademy.hotel.core.mappers;

import com.tinqinacademy.hotel.api.operations.signup.SignUpInput;
import com.tinqinacademy.hotel.persistence.models.user.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User SignUpInputToUser(SignUpInput signUpInput);
}
