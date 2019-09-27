package org.lint.azzert;

import org.lint.azzert.context.Context;

public interface LintCommand<T> {

    default T execute(Context context) throws Exception{return null;}
}
