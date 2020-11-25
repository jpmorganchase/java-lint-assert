package org.lint.azzert;

import org.lint.azzert.command.FindTestMethodsCommand;
import org.lint.azzert.command.RemoveMethodsThatAreNotTestsCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.strategy.output.PrintMode;

public class TestUtil {
    public static Context buildContext(String packageName) throws Exception {
        final Context context = new ContextBuilder().build();
        final LintAssertBuildParameters params = new LintAssertBuildParameters(
                packageName, false, true, PrintMode.ALL.name());

        new FindTestMethodsCommand(null, params)
                .withSuccessor(new RemoveMethodsThatAreNotTestsCommand())
                .execute(context);
        return context;
    }
}
