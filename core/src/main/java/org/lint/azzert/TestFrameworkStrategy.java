package org.lint.azzert;

import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.strategy.AnnotationDecorator;

import java.util.List;
import java.util.Set;

public interface TestFrameworkStrategy {

    String getSupportedFramework();

    List<String> getAssertApis();

    AnnotationDecorator getAnnotationDecorator(Set<AnnotationMetadata> annotations);

    boolean isDisabledMethod(MethodMetadata methodMetadata);

    default boolean isDisabledClass(MethodMetadata methodMetadata){
        return false;
    }

    default void removeAllNotAssertCalls(MethodMetadata methodMetadata){
        List<MethodCallMetadata> methodCalls = methodMetadata.getMethodCalls();
        methodCalls.removeIf(m -> getAssertApis().stream().filter(api -> m.getOwnerPackage().contains(api)).count() == 0);
    }

    default boolean isTest(MethodMetadata context){
        return context.getAnnotations().stream().filter(a -> a.getAnnotationName().equals(getSupportedFramework())).findAny().isPresent();
    }

}
