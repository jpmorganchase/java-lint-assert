package org.lint.azzert.strategy.framework;

import org.javatuples.Pair;
import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class TestNgStrategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework() { return "Lorg/testng/annotations/Test;"; }

    @Override
    public String getAssertApi(){
        return "org.testng";
    }

    @Override
    public boolean isDisabledMethod(MethodMetadata methodMetadata) {
        return isDisabled(methodMetadata.getAnnotations());
    }

    @Override
    public boolean isDisabledClass(MethodMetadata methodMetadata){
        return isDisabled(methodMetadata.getClassMetadata().getAnnotations());
    }

    private boolean isDisabled(Set<AnnotationMetadata> annotations) {
        if (annotations.isEmpty())
            return false;

        AnnotationMetadata annotation = annotations.stream().filter(a ->
                a.getAnnotationName().equals(this.getSupportedFramework())
        ).findAny().get();

        return annotation != null && annotation.getParameters().contains(new Pair<>("enabled", false));
    }
}
