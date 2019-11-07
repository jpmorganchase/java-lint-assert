package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;

public class ExemptDisabledMethodsCommand implements LintCommand<Void> {

    @Override
    //remove all disabled tests
    public Void execute(final Context context){
        //FIXME::proxy the method context
        context.getMethods().removeIf(method -> method.getTestFramework().isDisabledMethod(method));
        return null;
    }
}
