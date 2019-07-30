package org.lint.azzert.command;

import org.lint.azzert.context.Context;
import org.lint.azzert.context.TestMethodContext;

import java.util.Set;

public class ExemptDisabledTestsCommand implements LintCommand<Set<TestMethodContext>>{

    @Override
    public Set<TestMethodContext> execute(final Context context){
        return context.getMethodContexts();
    }
}
