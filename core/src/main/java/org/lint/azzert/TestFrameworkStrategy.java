package org.lint.azzert;

import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.List;

public interface TestFrameworkStrategy {

    String getSupportedFramework();

    List<String> getAssertApis();

    boolean isDisabledMethod(MethodMetadata methodMetadata);

    default boolean isDisabledClass(MethodMetadata methodMetadata){
        return false;
    }

    default void removeCallsThatAreNotAsserts(MethodMetadata methodMetadata){
        List<MethodCallMetadata> methodCalls = methodMetadata.getMethodCalls();
        methodCalls.removeIf(m -> getAssertApis().stream().filter(api -> m.getOwnerPackage().contains(api)).count() == 0);
    }

    default boolean isTest(MethodMetadata context){
        return context.getAnnotations().stream().filter(a -> a.getAnnotationName().equals(getSupportedFramework())).findAny().isPresent();
    }

}
