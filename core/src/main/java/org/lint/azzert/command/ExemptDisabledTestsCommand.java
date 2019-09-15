package org.lint.azzert.command;

import org.lint.azzert.context.Context;

import java.util.Collections;

public class ExemptDisabledTestsCommand implements LintCommand{

    @Override
    //remove all disabled tests
    public Void execute(final Context context){
        context.getMethodContexts().removeIf(
                method -> ! Collections.disjoint(context.getSupportedExemptApis(), method.getAnnotations())
        );

        // Test NG has ignore as attribute to test annotation
        context.getMethodContexts().removeIf(
                method -> method.isIgnored()
        );
        return null;
    }
}
