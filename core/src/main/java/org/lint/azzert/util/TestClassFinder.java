package org.lint.azzert.util;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//AN:: @see {https://github.com/classgraph/classgraph/wiki}
public final class TestClassFinder {
	
	private final ClassGraph classGraph;
    
	private  ClassLoader classLoader;
	
    public TestClassFinder() {
    	classGraph = new ClassGraph()
                 .enableClassInfo()
                 	.disableJarScanning()
                 		.ignoreClassVisibility();
    }
    
    public void setClassLoader(ClassLoader classLoader) {
    	this.classLoader = classLoader;
    	if (classLoader != null) classGraph.overrideClassLoaders(classLoader);
    }

    public void setVerbose(boolean verbose) {
    	if (verbose) classGraph.verbose();
    }
    
    public void setRootPackageName(String packageName) {
    	if (packageName != null) classGraph.whitelistPackages();
    }

    public  List<URL> getClasses() {
        final List<URL> classes = new ArrayList<>();
        
        final ClassInfoList list = getClassInfoList();
        for (ClassInfo c : list) {
        	if(c.getResource() != null) {
	        //	System.out.println(c.getResource().getURI());
	        	//classes.add(c.getClasspathElementURL());
	        	classes.add(c.getResource().getURL());
        	}
        }
        return classes;
    }

    private String classToResourceName(String className) {
        String resourceName = className.replace('.', '/');

        //handle nested classes:  com.jpmorgan.java.lint.azzert.TestLintPluginTest$PlaceholderTest
        int i = resourceName.indexOf("$");
        if (i > -1){
            resourceName = resourceName.substring(0, i);
        }
        resourceName += ".class";
        return resourceName;
    }

    private ClassInfoList getClassInfoList() {
    	try (ScanResult scanResult = classGraph.scan()) {
            return scanResult.getAllClasses();
        }
    }

}
