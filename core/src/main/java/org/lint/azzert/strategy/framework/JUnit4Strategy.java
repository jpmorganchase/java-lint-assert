package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JUnit4Strategy implements TestFrameworkStrategy {

    //FIXME::amsothe same code as JUnit5Strategy
    @Override
    public boolean isDisabled(MethodMetadata methodMetadata) {
        Set<AnnotationMetadata> annotations = methodMetadata.getAnnotations();
        return annotations.stream().filter(a -> a.getAnnotationName().contains("Lorg/junit/Ignore;")).findAny().isPresent();
    }

    @Override
    public String getSupportedFramework(){return "Lorg/junit/Test;";}

    @Override
    public List<String> getAssertApis(){return Arrays.asList("org.junit");}

}
