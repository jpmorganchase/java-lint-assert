package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;

public class RemoveMethodsThatAreNotTests implements LintCommand<Void> {

    @Override
    public Void execute(final Context context) {
        context.getMethods().removeIf(
                m -> (
                        !(
                                !m.getAnnotations().isEmpty() //a test method must be annotated (i.e. @Test)
                                        && m.getVisible() // a test must be explicitly declared (not inherited)
                                        && m.getTestFramework().isTest(m)) //i should be able to handle this framework
                )
        );

        return null;
    }
}
