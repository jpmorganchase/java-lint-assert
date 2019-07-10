package com.lint.azzert.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//AN:: @see {https://github.com/classgraph/classgraph/wiki}
public final class TestClassFinder {

    public static final ClassLoader CLASS_LOADER = TestClassFinder.class.getClassLoader();

    public static ClassInfoList getClassInfoList(String packageName) {

        try (ScanResult scanResult =                // Assign scanResult in try-with-resources
                     new ClassGraph()                    // Create a new ClassGraph instance
//                             .verbose()                      // If you want to enable logging to stderr
                             .enableClassInfo()
//                             .enableAllInfo()
                             .whitelistPackages(packageName)   // Scan com.xyz and subpackages
                             .scan()) {                      // Perform the scan and return a ScanResult
            return scanResult.getAllClasses();
        }
    }

    public static List<URL> getClasses(String name) throws IOException {
        final List<URL> classes = new ArrayList<>();

        ClassInfoList list = TestClassFinder.getClassInfoList(name);
        for (ClassInfo c : list) {
            String className = c.getName();
            String resourceName = className.replace('.', '/') + ".class";
            URL url = CLASS_LOADER.getResource(resourceName);

            classes.add(url);
        }
        return classes;
    }
}
