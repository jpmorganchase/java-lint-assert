package org.lint.azzert;

import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.List;

public interface TestFrameworkStrategy {

    String getSupportedFramework();

    String getAssertApi();

    boolean isDisabledMethod(MethodMetadata methodMetadata);

    default boolean isDisabledClass(MethodMetadata methodMetadata){
        return false;
    }

    default void removeCallsThatAreNotAsserts(MethodMetadata methodMetadata, Context context){
        List<MethodCallMetadata> methodCalls = methodMetadata.getMethodCalls();
        methodCalls.removeIf(m ->
                ! (m.getFullyQualifiedPackageName().contains(getAssertApi())
                        || m.isInOneOfExtLibs(context.getExtensionLibPackages())));
    }

    default boolean isTest(MethodMetadata context){
        return context.getAnnotations().stream().filter(a -> a.getAnnotationName()
                .equals(getSupportedFramework())).findAny().isPresent();
    }

}
