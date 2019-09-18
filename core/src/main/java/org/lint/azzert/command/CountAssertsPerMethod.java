package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;

public class CountAssertsPerMethod implements LintCommand<Void> {

    @Override
    //remove all calls within each method that are not asserts
    public Void execute(final Context context) {
        context.getMethodContexts().forEach(m -> m.getTestFramework().removeMethodsThatAreNotAsserts(m));
        return null;
    }
}
