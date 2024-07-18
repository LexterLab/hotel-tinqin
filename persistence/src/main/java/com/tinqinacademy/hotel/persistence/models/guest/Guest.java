package com.tinqinacademy.hotel.persistence.models.guest;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guest {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String idCardNo;
    private String idCardValidity;
    private String idCardIssueAuthority;
    private String idCardIssueDate;
}
