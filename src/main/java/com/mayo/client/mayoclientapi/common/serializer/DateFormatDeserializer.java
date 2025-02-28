package com.mayo.client.mayoclientapi.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.cloud.Timestamp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatDeserializer extends JsonDeserializer<Timestamp> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Timestamp deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String date = parser.getText();

        LocalDate localDate = LocalDate.parse(date, FORMATTER);

        return Timestamp.ofTimeSecondsAndNanos(
                localDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toEpochSecond(), 0
        );
    }
}
