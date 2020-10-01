package org.lint.azzert.processor;

import org.lint.azzert.strategy.output.PrintMode;

public class LintAssertBuildParameters {
    private final boolean verbose;
    private final boolean includeClasspathJars;
    private final String packageName;
    private final PrintMode printMode;

    public LintAssertBuildParameters(String packageName, boolean verbose, boolean includeClasspathJars, String printMode) {
        this.packageName = packageName;
        this.verbose = verbose;
        this.includeClasspathJars = includeClasspathJars;
        this.printMode = PrintMode.valueOf(printMode);
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean doIncludeClasspathJars() {
        return includeClasspathJars;
    }

    public String getPackageName() {
        return packageName;
    }

    public PrintMode getPrintMode() { return printMode; }

}
