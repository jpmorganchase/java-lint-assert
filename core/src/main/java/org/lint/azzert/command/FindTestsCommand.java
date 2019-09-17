package org.lint.azzert.command;

import org.javatuples.Pair;
import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindTestsCommand implements LintCommand<Void> {

    private final ClassLoader classLoader;
    private final Set<LintCommand> successors;

    private String packageName;
    private boolean verbose;

    public FindTestsCommand(ClassLoader classLoader, Pair<String, Boolean> params ){
        this.classLoader = classLoader;
        if (params != null) {
            this.packageName = params.getValue0();
            this.verbose = params.getValue1();
        }
        this.successors = new HashSet<>();
    }

    public FindTestsCommand withSuccessor(LintCommand successor){
        this.successors.add(successor);
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

        for (LintCommand successor : successors){
            successor.execute(context);
        }

        return null;
    }
}
