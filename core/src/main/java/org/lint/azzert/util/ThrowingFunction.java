package org.lint.azzert.util;

import java.util.function.Function;

//credit to https://github.com/pivovarit/articles/tree/master/java-sneaky-throws-lambda
public interface ThrowingFunction<T, R> {

    R apply(T t) throws Exception;

    @SuppressWarnings("unchecked")
    static <T extends Exception, R> R sneakyThrow(Exception t) throws T {
        throw (T) t;
    }

    static <T, R> Function<T, R> unchecked(ThrowingFunction<T, R> f) {
        return t -> {
            try {
                return f.apply(t);
            } catch (Exception ex) {
                return ThrowingFunction.sneakyThrow(ex);
            }
        };
    }
}