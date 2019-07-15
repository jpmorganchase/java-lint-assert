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

    public static final ClassLoader CLASS_LOADER = TestClassFinder.class.getClassLoader();

    public static List<URL> getClasses(String classpath, String name) {
        final List<URL> classes = new ArrayList<>();

        ClassInfoList list = TestClassFinder.getClassInfoList(classpath, name);
        for (ClassInfo c : list) {
            String className = c.getName();
            String resourceName = className.replace('.', '/') + ".class";
            URL url = CLASS_LOADER.getResource(resourceName);

            classes.add(url);
        }
        return classes;
    }

    protected static ClassInfoList getClassInfoList(String classpath, String packageName) {

        final ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                .whitelistPackages(packageName)
                //.overrideClasspath(classpath)
                ;

        try (ScanResult scanResult = classGraph.scan()) {
            return scanResult.getAllClasses();
        }
    }
}
