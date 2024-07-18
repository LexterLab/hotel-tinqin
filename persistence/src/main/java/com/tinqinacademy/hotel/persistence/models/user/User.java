package com.tinqinacademy.hotel.persistence.models.user;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
