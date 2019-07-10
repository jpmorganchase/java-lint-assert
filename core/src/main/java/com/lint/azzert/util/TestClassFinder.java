package com.lint.azzert.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.List;


//AN:: @see {https://github.com/classgraph/classgraph/wiki}
public final class TestClassFinder {

    /**
     * @return list of fully qualified class names
     */
    public static List<String> getClasses(String packageName) {


        List<String> classes = new ArrayList<>();

        try (ScanResult scanResult =                // Assign scanResult in try-with-resources
                     new ClassGraph()                    // Create a new ClassGraph instance
//                             .verbose()                      // If you want to enable logging to stderr
                             .enableClassInfo()
//                             .enableAllInfo()
                             .whitelistPackages(packageName)   // Scan com.xyz and subpackages
                             .scan()) {                      // Perform the scan and return a ScanResult
            ClassInfoList routeClassInfoList = scanResult.getAllClasses();

            for (ClassInfo classInfo : routeClassInfoList) {
                classes.add(classInfo.loadClass().getName());
            }
        }
        return classes;
    }
}
