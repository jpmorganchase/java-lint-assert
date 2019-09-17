package org.lint.azzert.framework.strategy;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class JUnit4Strategy implements TestFrameworkStrategy {

    @Override
    public String getSupportedFramework(){return "Lorg/junit/Test;";}

    @Override
    public boolean isDisabled(MethodMetadata context) {
        Set<String> annotations = context.getAnnotations();
        return annotations.contains("Lorg/junit/Ignore;");
    }

    @Override
    public int hashCode() {
        return getSupportedFramework().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != JUnit4Strategy.class)
            return false;

        JUnit4Strategy other = (JUnit4Strategy)obj;
        return getSupportedFramework().equals(other.getSupportedFramework());
    }
}
