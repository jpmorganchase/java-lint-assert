package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.strategy.ConsoleOutputStrategy;
import com.lint.azzert.util.TestClassFinder;
import com.lint.azzert.visitor.LintAssertClassVisitor;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.json.simple.parser.ParseException;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.util.List;

public class LintAssertTask extends DefaultTask {

    private String classPath;

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @TaskAction
    void lint_azzert() throws IOException, ParseException {
        System.out.printf("Searching for tests in classpath: %s \n", this.classPath);

        Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        List<String> classes = TestClassFinder.getClasses(this.getClass().getPackage().getName());
        for (String className : classes) {
            ClassReader classReader = new ClassReader(className);
            classReader.accept(classVisitor, 0);
        }

        String output = new ConsoleOutputStrategy(context.getTestMethodsContext()).render();
        System.out.println(output);
    }
}
