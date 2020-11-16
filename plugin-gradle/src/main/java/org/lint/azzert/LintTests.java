package org.lint.azzert;

import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.PrintMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLClassLoader;
import java.util.Set;

public class LintTests {
    private final Logger log = LoggerFactory.getLogger(LintTests.class);

    private ClassLoader classLoader;

    private String packageName;
    private boolean verbose;
    private boolean includeClasspathJars;
    private String printMode = PrintMode.ALL.name();

    public Set<MethodMetadata> lintAssert() throws Exception {

        log.info("Searching for tests in package:" + this.packageName);

        return new LintAssertProcessor(classLoader, new LintAssertBuildParameters(packageName, verbose, includeClasspathJars, "ALL")).process();
    }

    public void setClassLoader(URLClassLoader urlClassLoader) {
        this.classLoader = urlClassLoader;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isIncludeClasspathJars() {
        return includeClasspathJars;
    }

    public void setIncludeClasspathJars(boolean includeClasspathJars) {
        this.includeClasspathJars = includeClasspathJars;
    }

    public String getPrintMode() {
        return printMode;
    }

    public void setPrintMode(String printMode) {
        this.printMode = printMode;
    }

}
