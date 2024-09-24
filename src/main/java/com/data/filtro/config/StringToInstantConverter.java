package com.data.filtro.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class StringToInstantConverter implements Converter<String, Instant> {

    @Override
    public Instant convert(String source) {
        LocalDate localDate = LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
        return localDate.atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
