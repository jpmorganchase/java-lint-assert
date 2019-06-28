package com.lint._assert.util;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class PropertiesLoader {

    public static Object load(String pathToPropertiesFile) throws IOException, ParseException {
        InputStream reader = PropertiesLoader.class.getResourceAsStream(pathToPropertiesFile);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(new InputStreamReader(reader, StandardCharsets.UTF_8));
    }

}