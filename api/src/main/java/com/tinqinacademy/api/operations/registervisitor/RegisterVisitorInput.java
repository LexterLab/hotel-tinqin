package com.tinqinacademy.api.operations.registervisitor;

import com.tinqinacademy.api.operations.visitor.VisitorInput;
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
