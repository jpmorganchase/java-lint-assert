package org.lint.azzert.processor;

import org.lint.azzert.AssertProcessor;
import org.lint.azzert.command.*;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class LintAssertProcessor implements AssertProcessor<Set<MethodMetadata>> {

    private final ClassLoader classLoader;
    private final LintAssertBuildParameters params;

    public LintAssertProcessor(ClassLoader classLoader, LintAssertBuildParameters params){
        this.classLoader = classLoader;
        this.params = params;
    }
    
    @Override
    public Set<MethodMetadata> process() throws Exception {
        final Context context = new ContextBuilder().build();
        new FindTestMethodsCommand(classLoader, params)
                .withSuccessor(new ExemptDisabledClassesCommand())
                    .withSuccessor(new RemoveMethodsThatAreNotTests())
                        .withSuccessor(new ExemptDisabledMethodsCommand())
                            .withSuccessor(new CountAssertsPerMethod())
                                .execute(context);

        return context.getMethods();
    }
}
