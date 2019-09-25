package org.lint.azzert.strategy.framework;

import org.javatuples.Pair;
import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.Arrays;
import java.util.List;

public class TestNgStrategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework() { return "Lorg/testng/annotations/Test;"; }

    @Override
    public List<String> getAssertApis(){ return Arrays.asList("org.testng");}

    @Override
    public boolean isDisabled(MethodMetadata methodMetadata) {
        AnnotationMetadata annotation = methodMetadata.getAnnotations().stream().filter(a ->
                a.getAnnotationName().equals(this.getSupportedFramework())
        ).findAny().get();

        return annotation != null && annotation.getParameters().contains(new Pair<>("enabled", false));
    }
}
