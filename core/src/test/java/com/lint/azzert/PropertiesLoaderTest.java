package com.lint.azzert;

import com.lint.azzert.util.PropertiesLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertNotNull;

class PropertiesLoaderTest {

    @Test
    void test() throws Exception {
        JSONObject jsonObject = (JSONObject) PropertiesLoader.load("/application-properties.json");

//        System.out.println(jsonObject.get("assert_api"));
//        System.out.println(jsonObject.get("test_framework"));
        List<String> l = (List<String>) ((JSONArray)jsonObject.get("assert_api")).stream().map(Object::toString).collect(Collectors.toList());
//        l.forEach(System.out::println);

        assertNotNull(jsonObject.get("assert_api"));
        assertNotNull(jsonObject.get("test_framework"));

    }

}
