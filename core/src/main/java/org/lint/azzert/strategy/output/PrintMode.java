package org.lint.azzert.strategy.output;

import org.lint.azzert.OutputFormatterCommand;
import org.lint.azzert.command.output.OutputOnlyAssertlessMethods;

public enum PrintMode {

    ASSERTLESS_ONLY(new OutputOnlyAssertlessMethods()), ALL(new OutputFormatterCommand(){});

    private final OutputFormatterCommand command;
    private PrintMode(OutputFormatterCommand command){
        this.command = command;
    }

    public OutputFormatterCommand getOutputFormatterCommand(){
        return command;
    }
}
