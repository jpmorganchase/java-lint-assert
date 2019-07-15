package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.strategy.ConsoleOutputStrategy;
import com.lint.azzert.util.TestClassFinder;
import com.lint.azzert.visitor.LintAssertClassVisitor;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.testing.Test;
import org.json.simple.parser.ParseException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class LintAssertTask extends Test {

    private String packageName;

    private String classpath;

    public void setClasspath(String classpath) { this.classpath = classpath; }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @TaskAction
    void lint_azzert() throws IOException, ParseException {

        final Logger log = getLogger();

        log.error("Searching for tests in package:" + this.packageName + " in classpath:" + classpath);

        Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        List<URL> list = TestClassFinder.getClasses(this.classpath, this.packageName);
        log.error("Found classes:" + list);

        for (URL c : list) {
            log.error("file:: " + c);
            ClassReader classReader = new ClassReader(c.openStream());

            classReader.accept(classVisitor, 0);
        }

        String output = new ConsoleOutputStrategy(context.getTestMethodsContext()).render();
        log.error(output);
    }
}
