package com.tinqinacademy.hotel.models.mappers;

import com.tinqinacademy.hotel.operations.getvisitorreport.GetVisitorsReportInput;
import com.tinqinacademy.hotel.operations.visitor.VisitorOutput;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface VisitorReportMapper {
    VisitorReportMapper INSTANCE = Mappers.getMapper(VisitorReportMapper.class);
    VisitorOutput inputToOutput(GetVisitorsReportInput input);

}
