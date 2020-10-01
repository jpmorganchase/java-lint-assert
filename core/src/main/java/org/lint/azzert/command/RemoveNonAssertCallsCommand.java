package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;

public class RemoveNonAssertCallsCommand implements LintCommand<Void> {

    @Override
    //remove all calls within each method that are not asserts
    public Void execute(final Context context) {
        context.getMethods().forEach(m -> m.getTestFramework().removeAllNotAssertCalls(m));
        return null;
    }
}
