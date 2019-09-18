package org.lint.azzert.strategy.framework;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

import java.util.Arrays;
import java.util.List;

public class TestNgStrategy implements TestFrameworkStrategy {

    @Override
    public boolean isDisabled(MethodMetadata context) { return false;}

    @Override
    public String getSupportedFramework() { return "Lorg/testng/annotations/Test;"; }

    @Override
    public List<String> getAssertApis(){ return Arrays.asList("org.testng");}
}
