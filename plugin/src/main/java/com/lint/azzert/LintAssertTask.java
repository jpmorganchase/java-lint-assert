package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.strategy.ConsoleOutputStrategy;
import com.lint.azzert.util.TestClassFinder;
import com.lint.azzert.visitor.LintAssertClassVisitor;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.json.simple.parser.ParseException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;

public class LintAssertTask extends DefaultTask {

    private String packageName;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @TaskAction
    void lint_azzert() throws IOException, ParseException {

        final Logger log = getLogger();

        log.error("Searching for tests in package:" + this.packageName);

        Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        ClassInfoList list = TestClassFinder.getClassInfoList(this.packageName);
        log.error("Found classes:" + list);

        for (ClassInfo c : list) {
            String className = c.getName();
            String resourceName = className.replace('.', '/') + ".class";
            URL url = this.getClass().getClassLoader().getResource(resourceName);
            log.error(resourceName + "==" + url);
            ClassReader classReader = new ClassReader(url.openStream());

            classReader.accept(classVisitor, 0);
        }

        String output = new ConsoleOutputStrategy(context.getTestMethodsContext()).render();
        log.error(output);
    }
}
