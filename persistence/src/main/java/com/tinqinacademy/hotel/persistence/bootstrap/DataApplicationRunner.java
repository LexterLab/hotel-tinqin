//package com.tinqinacademy.hotel.persistence.bootstrap;
//
//import com.tinqinacademy.hotel.api.models.constants.BedSize;
//import com.tinqinacademy.hotel.persistence.models.bed.Bed;
//import com.tinqinacademy.hotel.persistence.repositories.BedRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//public class DataApplicationRunner implements ApplicationRunner {
//    private final BedRepository bedRepository;
//
//    @Override
//    public void run(ApplicationArguments args)  {
//        for (BedSize bedSize : BedSize.values()) {
//            Optional<Bed> existingBed = bedRepository.findByBedSize(bedSize);
//            if (existingBed.isEmpty() && !bedSize.toString().isEmpty()) {
//                Bed bed = Bed.builder()
//                        .bedCapacity(bedSize.getCapacity())
//                        .bedSize(bedSize)
//                        .id(UUID.randomUUID())
//                        .build();
//                bedRepository.save(bed);
//            }
//        }
//    }
//}
