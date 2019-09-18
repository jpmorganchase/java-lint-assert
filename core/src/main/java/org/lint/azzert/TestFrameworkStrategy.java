package org.lint.azzert;

import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TestFrameworkStrategy {

    boolean isDisabled(MethodMetadata context);

    String getSupportedFramework();

    default Set<String> getSupportedAssertApis() {
        return new HashSet<>(getAssertApis());
    }

    default List<String> getAssertApis(){return new ArrayList<>();}

    default void removeMethodsThatAreNotAsserts(MethodMetadata methodMetadata){
        List<MethodCallMetadata> methodCalls = methodMetadata.getMethodCalls();
        methodCalls.removeIf(m -> ! getSupportedAssertApis().contains(m.getOwnerPackage()));
    }

    default boolean isTest(MethodMetadata context){
        return context.getAnnotations().contains(getSupportedFramework());
    }

}
