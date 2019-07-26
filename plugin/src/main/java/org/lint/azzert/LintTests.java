package org.lint.azzert;

import org.json.simple.parser.ParseException;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.TestMethodContext;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

public class LintTests {
    private Logger log = LoggerFactory.getLogger(LintTests.class);

    private String packageName;

    private ClassLoader classLoader;

    private boolean verbose;

    public Set<TestMethodContext> lintAssert() throws IOException, ParseException {

        log.info("Searching for tests in package:" + this.packageName);

        Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        TestClassFinder finder = new TestClassFinder();
        if (classLoader != null) {
            finder.setClassLoader(classLoader);
        }

        finder.setVerbose(this.verbose);

        List<URL> list = finder.getClasses(this.packageName);
        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        return context.getMethodContexts();
    }

    public void setClassLoader(URLClassLoader urlClassLoader) {
        this.classLoader = urlClassLoader;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setVerbose(boolean verbose){
        this.verbose = verbose;
    }
}
