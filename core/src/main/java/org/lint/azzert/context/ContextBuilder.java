package org.lint.azzert.context;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.util.PropertiesLoader;
import org.objectweb.asm.Opcodes;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.lint.azzert.util.ThrowingFunction.unchecked;

public final class ContextBuilder {

    private Context context;

    public Context build() throws Exception {
        context = new Context(Opcodes.ASM7);
        this.initFromJsonProperties();
        return context;
    }

    private void initFromJsonProperties() throws Exception {

        JSONObject json = (JSONObject) PropertiesLoader.load("/application-properties.json");

        final BiFunction<JSONObject, String, Set<String>> function =
                (jsn, key) -> (Set<String>)((JSONArray) jsn.get(key)).stream().map(Object::toString).collect(Collectors.toSet());

        Set<String> frameworkNames = function.apply(json, "test_framework");
        Set<TestFrameworkStrategy> frameworks = frameworkNames.stream().map(
                unchecked(fn -> (TestFrameworkStrategy) Class.forName(fn).newInstance()))
                .collect(Collectors.toSet());
        context.addSupportedTestFrameworks(frameworks);

        Set<String> verificationLibs = function.apply(json, "verify_libs");
        context.addVerificationExtensions(verificationLibs);
    }
}
