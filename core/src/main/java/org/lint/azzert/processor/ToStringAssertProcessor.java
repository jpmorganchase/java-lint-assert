package org.lint.azzert.processor;

import org.javatuples.Pair;
import org.lint.azzert.AssertProcessor;
import org.lint.azzert.command.CountAssertsPerMethod;
import org.lint.azzert.command.ExemptDisabledTestsCommand;
import org.lint.azzert.command.FindAllMethodsCommand;
import org.lint.azzert.command.RemoveMethodsThatAreNotTests;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class ToStringAssertProcessor implements AssertProcessor<Set<MethodMetadata>> {

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
    public Set<MethodMetadata> process() throws Exception {

        final Context context = new ContextBuilder().build();

        new FindAllMethodsCommand(classLoader, params)
                .withSuccessor(new RemoveMethodsThatAreNotTests())
                    .withSuccessor(new ExemptDisabledTestsCommand())
                        .withSuccessor(new CountAssertsPerMethod())
                            .execute(context);

        return context.getMethodContexts();
    }
}
