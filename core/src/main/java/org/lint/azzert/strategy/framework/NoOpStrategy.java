package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

public class NoOpStrategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework() {
        return null;
    }

    @Override
    public boolean isDisabled(MethodMetadata context) {
        return false;
    }
}
