package com.data.filtro.Util;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FmtInstant extends CellProcessorAdaptor {

    private final DateTimeFormatter formatter;
    private final ZoneId zoneId;

    public FmtInstant(String pattern) {
        this(pattern, ZoneId.of("Asia/Ho_Chi_Minh"));
    }

    public FmtInstant(String pattern, ZoneId zoneId) {
        super();
        this.formatter = DateTimeFormatter.ofPattern(pattern);
        this.zoneId = zoneId;
    }

    @Override
    public Object execute(Object value, CsvContext context) {
        if (value == null) {
            return null;
        }

        if (!(value instanceof Instant)) {
            throw new SuperCsvCellProcessorException(Instant.class, value, context, this);
        }

        return formatter.format(((Instant) value).atZone(zoneId));
    }
}

