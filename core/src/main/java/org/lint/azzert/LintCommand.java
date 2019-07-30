package org.lint.azzert;

public interface LintCommand<T, K> {
    K execute() throws Exception;
}
