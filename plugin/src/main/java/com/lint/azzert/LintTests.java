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
import java.util.List;

public class LintTests {
    Logger log = LoggerFactory.getLogger(LintTests.class);

    private String packageName;

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    String lint() throws IOException, ParseException {

        log.info("Searching for tests in package:" + this.packageName);

        Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        List<URL> list = TestClassFinder.getClasses(this.packageName);
        log.info("Found classes:" + list);

        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        return new ConsoleOutputStrategy(context.getTestMethodsContext()).render();
    }
}
