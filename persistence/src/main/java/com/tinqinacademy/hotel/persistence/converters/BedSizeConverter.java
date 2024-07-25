package com.tinqinacademy.hotel.persistence.converters;

import com.tinqinacademy.hotel.api.models.constants.BedSize;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BedSizeConverter implements AttributeConverter<BedSize, String> {
    @Override
    public String convertToDatabaseColumn(BedSize bedSize) {
        return bedSize == null ? null : bedSize.toString();
    }

    @Override
    public BedSize convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        for (BedSize size : BedSize.values()) {
            if (size.toString().equals(s)) {
                return size;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + s);
    }
}
