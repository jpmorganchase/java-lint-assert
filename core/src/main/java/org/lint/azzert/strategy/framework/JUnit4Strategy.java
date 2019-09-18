package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JUnit4Strategy implements TestFrameworkStrategy {

    @Override
    public boolean isDisabled(MethodMetadata context) {
        Set<String> annotations = context.getAnnotations();
        return annotations.contains("Lorg/junit/Ignore;");
    }

    @Override
    public String getSupportedFramework(){return "Lorg/junit/Test;";}

    @Override
    public List<String> getAssertApis(){return Arrays.asList("org.junit");}

}
