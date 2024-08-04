package com.tinqinacademy.hotel.persistence.models.guest;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate birthday;
    @Column(nullable = false, unique = true)
    private String idCardNo;
    @Column(nullable = false)
    private LocalDate idCardValidity;
    @Column(nullable = false)
    private String idCardIssueAuthority;
    @Column(nullable = false)
    private LocalDate idCardIssueDate;



}
