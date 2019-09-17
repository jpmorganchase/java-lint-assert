package org.lint.azzert;

import org.lint.azzert.context.MethodMetadata;

public interface TestFrameworkStrategy {

    String getSupportedFramework();

    boolean isDisabled(MethodMetadata context);
}
