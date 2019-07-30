package org.lint.azzert.command;

import org.lint.azzert.context.Context;

public interface LintCommand<T> {

    default T execute(Context context) throws Exception{return null;}
}
