package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class RemoveMethodsWithAssertsCommand implements LintCommand<Void> {

    @Override
    public Void execute(Context context) {
        Set<MethodMetadata> testMethods = context.getMethods();
        testMethods.removeIf(m -> !m.getMethodCalls().isEmpty());
        return null;
    }
}
