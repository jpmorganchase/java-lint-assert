package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

import java.util.ArrayList;
import java.util.List;

public class NoOpStrategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework() {
        return null;
    }

    @Override
    public boolean isDisabled(MethodMetadata context) {
        return false;
    }

    @Override
    public List<String> getAssertApis(){return new ArrayList<>();}
}
