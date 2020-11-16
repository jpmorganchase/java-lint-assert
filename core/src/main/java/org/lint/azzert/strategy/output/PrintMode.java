package org.lint.azzert.strategy.output;

import org.lint.azzert.LintCommand;
import org.lint.azzert.command.RemoveMethodsWithAssertsCommand;

public enum PrintMode {

    ASSERTLESS_ONLY(new RemoveMethodsWithAssertsCommand()), ALL(new LintCommand(){});

    private final LintCommand command;
    private PrintMode(LintCommand command){
        this.command = command;
    }

    public LintCommand getCommand(){
        return command;
    }


}
