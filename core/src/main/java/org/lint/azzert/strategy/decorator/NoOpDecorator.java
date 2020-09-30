package org.lint.azzert.strategy.decorator;

import org.lint.azzert.strategy.AnnotationDecorator;

public class NoOpDecorator implements AnnotationDecorator {

    @Override
    public String getExpectedException() {
        return null;
    }

    @Override
    public int getExpectedExceptionLength() {
        return 0;
    }
}
