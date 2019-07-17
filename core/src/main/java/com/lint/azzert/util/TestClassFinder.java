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
            String className = c.getName();
//            System.out.println("className::" + className);

            String resourceName = className.replace('.', '/') + ".class";
            URL url = this.classLoader.getResource(resourceName);

            classes.add(url);
        }
        return classes;
    }

    protected ClassInfoList getClassInfoList(String packageName) {

//        URL[] urls = ((URLClassLoader)classLoader).getURLs();
//        for (int i = 0; i < urls.length; i++) {
//            System.out.println("url:::" +  urls[i].getPath());
//        }

        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                ;

        if (this.verbose) classGraph.verbose();

        try (ScanResult scanResult =
                     classGraph
                            // .verbose() //FIXME - line above doesn't seem to work
                             .whitelistPackages(packageName)      // Scan com.xyz and subpackages (omit to scan all packages)
                             .overrideClassLoaders(classLoader)
                             .scan()) {                   // Start the scan
            return scanResult.getAllClasses();
        }
    }

}
