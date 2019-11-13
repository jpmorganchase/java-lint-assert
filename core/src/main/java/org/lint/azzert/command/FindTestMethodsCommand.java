package org.lint.azzert.command;

import org.lint.azzert.LintCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.util.TestClassFinder;
import org.lint.azzert.visitor.LintAssertClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindTestMethodsCommand implements LintCommand<Void> {

    private final ClassLoader classLoader;
    private final Set<LintCommand> successors;

    private LintAssertBuildParameters params;

    public FindTestMethodsCommand(ClassLoader classLoader, LintAssertBuildParameters params ){
        this.classLoader = classLoader;
        if (params != null) {
            this.params = params;
        }
        this.successors = new HashSet<>();
    }

    public FindTestMethodsCommand withSuccessor(LintCommand successor){
        this.successors.add(successor);
        return this;
    }

    @Override
    public Void execute(final Context context) throws Exception {
        final ClassVisitor classVisitor = new LintAssertClassVisitor(context);

        final TestClassFinder finder = new TestClassFinder();
        finder.setClassLoader(classLoader);
        finder.setVerbose(params.isVerbose());
        finder.disableJarScanning( ! params.doIncludeClasspathJars());
        finder.setRootPackageName(params.getPackageName());

        List<URL> list = finder.getClasses();
        for (URL c : list) {
            ClassReader classReader = new ClassReader(c.openStream());
            classReader.accept(classVisitor, 0);
        }

        for (LintCommand successor : successors){
            successor.execute(context);
        }

//        context.getMethods().forEach(System.out::println);
        return null;
    }
}
