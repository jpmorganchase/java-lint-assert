package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;
import java.util.function.Function;

public class JUnit4Strategy implements TestFrameworkStrategy {

    protected final Function<Set<AnnotationMetadata>, Boolean> isDisabled = annotations1 ->
            annotations1.stream().filter(a ->
                    a.getAnnotationName().contains(getDisabledAnnotation())).findAny().isPresent();

    @Override
    public String getSupportedFramework(){
        return "Lorg/junit/Test;";
    }

    @Override
    public String getAssertApi(){
        return "org.junit";
    }

    public String getDisabledAnnotation(){
        return "Lorg/junit/Ignore;";
    }

    @Override
    public boolean isDisabledMethod(MethodMetadata methodMetadata) {
        Set<AnnotationMetadata> annotations = methodMetadata.getAnnotations();
        return isDisabled.apply(annotations);
    }

    @Override
    public boolean isDisabledClass(MethodMetadata methodMetadata){
        Set<AnnotationMetadata> annotations = methodMetadata.getClassMetadata().getAnnotations();
        return isDisabled.apply(annotations);
    }


}
