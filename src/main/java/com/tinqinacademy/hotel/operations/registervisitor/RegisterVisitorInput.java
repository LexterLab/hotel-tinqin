package com.tinqinacademy.hotel.operations.registervisitor;

import com.tinqinacademy.hotel.operations.visitor.VisitorInput;
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
