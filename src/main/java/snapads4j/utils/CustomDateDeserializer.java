/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @see {https://stackoverflow.com/questions/41037243/how-to-make-milliseconds-optional-in-jsonformat-for-timestamp-parsing-with-jack}
 */
public class CustomDateDeserializer extends StdDeserializer<Date> {
    private static final SimpleDateFormat withMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
    private static final SimpleDateFormat withoutTimezone = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        if (dateString.isEmpty()) {
            return null;
        }
        try {
            return withMillis.parse(dateString);
        } catch (ParseException e) {
            try {
                return withoutTimezone.parse(dateString);
            } catch (ParseException e1) {
                throw new RuntimeException("Impossible to parse date", e1);
            }
        }
    }
}// CustomDateDeserializer
