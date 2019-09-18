package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JUnit5Strategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework(){return "Lorg/junit/jupiter/api/Test;";}

    public List<String> getAssertApis(){return Arrays.asList("org.junit.jupiter.api");}

    @Override
    public boolean isDisabled(MethodMetadata context) {
        Set<String> annotations = context.getAnnotations();
        return annotations.contains("Lorg/junit/jupiter/api/Disabled;");
    }


    @Override
    public int hashCode() {
        return getSupportedFramework().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != JUnit5Strategy.class)
            return false;

        JUnit5Strategy other = (JUnit5Strategy)obj;
        return getSupportedFramework().equals(other.getSupportedFramework());
    }
}
