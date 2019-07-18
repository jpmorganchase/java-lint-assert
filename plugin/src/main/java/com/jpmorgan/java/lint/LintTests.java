package com.jpmorgan.java.lint;

import com.jpmorgan.java.lint.azzert.context.Context;
import com.jpmorgan.java.lint.azzert.context.ContextBuilder;
import com.jpmorgan.java.lint.azzert.strategy.ConsoleOutputStrategy;
import com.jpmorgan.java.lint.azzert.util.TestClassFinder;
import com.jpmorgan.java.lint.azzert.visitor.LintAssertClassVisitor;
import org.json.simple.parser.ParseException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class LintTests {
    private Logger log = LoggerFactory.getLogger(LintTests.class);

    private String packageName;

    private ClassLoader classLoader;

    private boolean verbose;

    public String lintAssert() throws IOException, ParseException {

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

        return new ConsoleOutputStrategy(context.getTestMethodsContext()).render(); //fixme: return context.getTest..., string in the plugin
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
