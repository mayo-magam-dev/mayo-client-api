package com.mayo.client.mayoclientapi.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.cloud.Timestamp;

import java.io.IOException;

public class FirestoreTimestampDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String date = parser.getText();
        return Timestamp.parseTimestamp(date);
    }
}
