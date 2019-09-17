package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;

public class RemoveMethodsThatAreNotTests implements LintCommand<Void> {

    @Override
    public Void execute(final Context context) {
        context.getMethodContexts().removeIf(
                methodContext -> (
                        methodContext.getAnnotations().size() == 0 //a test method must be annotated (i.e. @Test)
                        || ! methodContext.getVisible()
                        //FIXME:: or if none of annotations is recognizable by a testframeworkstrategy
                )
        );

        return null;
    }
}
