package org.lint.azzert.strategy;

public interface AnnotationDecorator {

    default boolean isExceptionExpected(){return false;}

    String getExpectedException();

    int getExpectedExceptionLength();
}
