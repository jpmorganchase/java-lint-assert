package org.lint.azzert;

public interface AssertProcessor<T> {
    T process() throws Exception;
}
