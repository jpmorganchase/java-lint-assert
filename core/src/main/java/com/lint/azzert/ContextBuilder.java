package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.util.PropertiesLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public final class ContextBuilder {

    private Context context;

    public Context build() throws IOException, ParseException {
        context = new Context(Opcodes.ASM7);
        this.initFromJsonProperties("/application-properties.json");
        return context;
    }

    private void initFromJsonProperties(String pathToPropertiesFile) throws IOException, ParseException {
        JSONObject json = (JSONObject) PropertiesLoader.load(pathToPropertiesFile);

        final BiFunction<JSONObject, String, List<String>> function =
                (jsonObject1, key1) -> (List<String>) (
                        (JSONArray) jsonObject1.get(key1)).stream().map(Object::toString).collect(Collectors.toList());

        context.addSupportedTestFrameworks(function.apply(json, "test_framework"));
        context.addSupportedAssertApis(function.apply(json, "assert_api"));
    }
}
