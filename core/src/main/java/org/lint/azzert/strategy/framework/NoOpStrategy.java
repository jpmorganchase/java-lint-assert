package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.strategy.AnnotationDecorator;
import org.lint.azzert.strategy.decorator.NoOpDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NoOpStrategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework() {
        return null;
    }

    @Override
    public boolean isDisabledMethod(MethodMetadata context) {
        return false;
    }

    @Override
    public List<String> getAssertApis(){return new ArrayList<>();}

    @Override
    public AnnotationDecorator getAnnotationDecorator(Set<AnnotationMetadata> annotations) { return new NoOpDecorator(); }
}
