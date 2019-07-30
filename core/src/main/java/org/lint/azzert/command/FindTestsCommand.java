package org.lint.azzert.command;

import org.javatuples.Pair;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.TestMethodContext;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.net.URL;
import java.util.List;
import java.util.Set;

public class FindTestsCommand implements LintCommand {

    private final ClassLoader classLoader;
    private String packageName;
    private boolean verbose;
    private LintCommand successor;

    public FindTestsCommand(ClassLoader classLoader, Pair<String, Boolean> params ){
        this.classLoader = classLoader;
        if (params != null) {
            this.packageName = params.getValue0();
            this.verbose = params.getValue1();
        }
    }

    public FindTestsCommand withSuccessor(LintCommand successor){
        this.successor = successor;
        return this;
    }

    @Override
    public Set<TestMethodContext> execute(final Context context) throws Exception {

        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        final TestClassFinder finder = new TestClassFinder();
        if (classLoader != null) finder.setClassLoader(classLoader);
        finder.setVerbose(this.verbose);

        List<URL> list = finder.getClasses(this.packageName);
        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        if (successor != null){
            successor.execute(context);
        }

        return context.getMethodContexts();
    }
}
