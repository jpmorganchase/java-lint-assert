package com.lint.azzert.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


//AN:: @see {https://github.com/classgraph/classgraph/wiki}
public final class TestClassFinder {


    static Logger log = LoggerFactory.getLogger(TestClassFinder.class);

    public static final ClassLoader CLASS_LOADER = TestClassFinder.class.getClassLoader();

    private final boolean verbose;

    TestClassFinder(boolean verbose){
        this.verbose = verbose;
    }

    public boolean isVerbose(){return verbose;}

    public  static List<URL> getClasses(String name) {
        return TestClassFinder.getClasses(name, false);
    }

    public  static List<URL> getClasses(String name, boolean verbose) {
        final List<URL> classes = new ArrayList<>();

        ClassInfoList list = new TestClassFinder(verbose).getClassInfoList(name);
        for (ClassInfo c : list) {
            String className = c.getName();
            String resourceName = className.replace('.', '/') + ".class";
            URL url = CLASS_LOADER.getResource(resourceName);

            classes.add(url);
        }
        return classes;
    }

    protected ClassInfoList getClassInfoList(String packageName) {

        ClassGraph classGraph = new ClassGraph()
                //.verbose()
                .enableClassInfo();

        if (isVerbose()) classGraph.verbose();

        try (ScanResult scanResult =
                     classGraph
                             .whitelistPackages(packageName)      // Scan com.xyz and subpackages (omit to scan all packages)
                             .scan()) {                   // Start the scan
            return scanResult.getAllClasses();
        }
    }
}
