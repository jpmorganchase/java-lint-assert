package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.strategy.ConsoleOutputStrategy;
import com.lint.azzert.util.TestClassFinder;
import com.lint.azzert.visitor.LintAssertClassVisitor;
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

    String lint() throws IOException, ParseException {

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

        return new ConsoleOutputStrategy(context.getTestMethodsContext()).render();
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
