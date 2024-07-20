package com.tinqinacademy.hotel.core.mappers;

import com.tinqinacademy.hotel.api.operations.getroom.BedOutput;
import com.tinqinacademy.hotel.persistence.models.bed.Bed;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BedMapper {
    BedMapper INSTANCE = Mappers.getMapper(BedMapper.class);

    BedOutput bedToBedOutput(Bed bed);
    List<BedOutput> bedsToBedOutputs(Iterable<Bed> beds);
}
