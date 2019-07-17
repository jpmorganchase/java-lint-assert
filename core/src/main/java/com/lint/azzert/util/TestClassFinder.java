package com.lint.azzert.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//AN:: @see {https://github.com/classgraph/classgraph/wiki}
public final class TestClassFinder {

    private boolean verbose;

    private ClassLoader classLoader;

    public TestClassFinder(){
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public  List<URL> getClasses(String name) {
        final List<URL> classes = new ArrayList<>();

        ClassInfoList list = getClassInfoList(name);
        for (ClassInfo c : list) {
            final String resourceName = classToResourceName(c.getName());
            URL url = this.classLoader.getResource(resourceName);
            classes.add(url);
        }

        return classes;
    }

    private String classToResourceName(String className) {
        String resourceName = className.replace('.', '/');

        //handle nested classes:  com.lint.azzert.TestLintPluginTest$PlaceholderTest
        int i = resourceName.indexOf("$");
        if (i > -1){
            resourceName = resourceName.substring(0, i);
        }
        resourceName += ".class";
        return resourceName;
    }

    private ClassInfoList getClassInfoList(String packageName) {

        ClassGraph classGraph = new ClassGraph()
                                    .enableClassInfo()
                                    .ignoreClassVisibility()
                                    .overrideClassLoaders(classLoader);

        if (this.verbose) classGraph.verbose();
        if (packageName != null) classGraph.whitelistPackages(packageName);

        try (ScanResult scanResult =
                     classGraph
//                             .whitelistPackages(packageName)
//                             .overrideClassLoaders(classLoader)
                             .scan()) {
            return scanResult.getAllClasses();
        }
    }

}
