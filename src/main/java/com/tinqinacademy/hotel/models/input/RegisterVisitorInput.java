package com.tinqinacademy.hotel.models.input;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterVisitorInput {
    List<VisitorInput> visitors;
}
