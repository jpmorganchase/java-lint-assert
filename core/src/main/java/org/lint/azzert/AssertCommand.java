package org.lint.azzert;

import org.javatuples.Pair;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.TestMethodContext;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.net.URL;
import java.util.List;
import java.util.Set;

public class AssertCommand implements LintCommand<ClassLoader, Set<TestMethodContext>> {

    private final ClassLoader classLoader;
    private String packageName;
    private boolean verbose;

    public AssertCommand(){
        this(null, null);
    }

    public AssertCommand(ClassLoader classLoader, Pair<String, Boolean> params ){
        this.classLoader = classLoader;
        if (params != null) {
            this.packageName = params.getValue0();
            this.verbose = params.getValue1();
        }
    }

    public Set<TestMethodContext> execute() throws Exception {

        final Context context = new ContextBuilder().build();
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        final TestClassFinder finder = new TestClassFinder();
        if (classLoader != null) finder.setClassLoader(classLoader);
        finder.setVerbose(this.verbose);

        List<URL> list = finder.getClasses(this.packageName);
        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        return context.getMethodContexts();
    }
}
