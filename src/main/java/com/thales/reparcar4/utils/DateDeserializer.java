package com.thales.reparcar4.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateDeserializer extends StdDeserializer {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    protected DateDeserializer(Class vc) {
        super(vc);
    }

    protected DateDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected DateDeserializer(StdDeserializer src) {
        super(src);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        String date = jsonParser.getText();
        try {
            return formatter.parse(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
