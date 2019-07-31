package org.lint.azzert;

import org.javatuples.Pair;
import org.lint.azzert.command.ExemptDisabledTestsCommand;
import org.lint.azzert.command.FindTestsCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.TestMethodContext;

import java.util.Set;

public class ToStringAssertProcessor implements AssertProcessor<Set<TestMethodContext>> {

    private final ClassLoader classLoader;
    private final Pair<String, Boolean> params;

    public ToStringAssertProcessor() {
        this(null, null);
    }

    public ToStringAssertProcessor(ClassLoader classLoader, Pair<String, Boolean> params ){
        this.classLoader = classLoader;
        this.params = params;
    }
    
    @Override
    public Set<TestMethodContext> process() throws Exception {

        final Context context = new ContextBuilder().build();

        new FindTestsCommand(classLoader, params)
                .withSuccessor(new ExemptDisabledTestsCommand())
                    .execute(context);

        return context.getMethodContexts();
    }
}
