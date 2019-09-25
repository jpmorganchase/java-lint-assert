package org.lint.azzert;

import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.List;

public interface TestFrameworkStrategy {

    String getSupportedFramework();

    List<String> getAssertApis();

    boolean isDisabled(MethodMetadata methodMetadata);

    default void removeAllNotAssertCalls(MethodMetadata methodMetadata){
        List<MethodCallMetadata> methodCalls = methodMetadata.getMethodCalls();
        methodCalls.removeIf(m -> ! getAssertApis().contains(m.getOwnerPackage()));
    }

    default boolean isTest(MethodMetadata context){
        return context.getAnnotations().stream().filter(a -> a.getAnnotationName().equals(getSupportedFramework())).findAny().isPresent();
    }

}
