package org.lint.azzert.command;

import org.javatuples.Pair;
import org.lint.azzert.context.Context;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.net.URL;
import java.util.List;

public class FindTestsCommand implements LintCommand<Void> {

    private final ClassLoader classLoader;
    private String packageName;
    private boolean verbose;
    private LintCommand<Void> successor;

    public FindTestsCommand(ClassLoader classLoader, Pair<String, Boolean> params ){
        this.classLoader = classLoader;
        if (params != null) {
            this.packageName = params.getValue0();
            this.verbose = params.getValue1();
        }
    }

    public FindTestsCommand withSuccessor(LintCommand<Void> successor){
        this.successor = successor;
        return this;
    }

    @Override
    public Void execute(final Context context) throws Exception {

        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        final TestClassFinder finder = new TestClassFinder();
        finder.setClassLoader(classLoader);
        finder.setVerbose(this.verbose);
        finder.setRootPackageName(this.packageName);

        List<URL> list = finder.getClasses();
        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        if (successor != null){
            successor.execute(context);
        }

        return null;
    }
}
