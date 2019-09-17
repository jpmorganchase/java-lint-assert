package org.lint.azzert;

import org.javatuples.Pair;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.ToStringAssertProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLClassLoader;
import java.util.Set;

public class LintTests {
    private final Logger log = LoggerFactory.getLogger(LintTests.class);

    private ClassLoader classLoader;

    private String packageName;
    private boolean verbose;

    public Set<MethodMetadata> lintAssert() throws Exception {

        log.info("Searching for tests in package:" + this.packageName);

        return new ToStringAssertProcessor(classLoader, new Pair(packageName, verbose)).process();
    }

    public void setClassLoader(URLClassLoader urlClassLoader) {
        this.classLoader = urlClassLoader;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVerbose(boolean verbose) { this.verbose = verbose; }
}
