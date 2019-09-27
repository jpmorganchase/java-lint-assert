package org.lint.azzert.processor;

public class LintAssertBuildParameters {
    private final boolean verbose;
    private final boolean includeClasspathJars;
    private final String packageName;

    public LintAssertBuildParameters(String packageName, boolean verbose, boolean includeClasspathJars) {
        this.packageName = packageName;
        this.verbose = verbose;
        this.includeClasspathJars = includeClasspathJars;
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

}
