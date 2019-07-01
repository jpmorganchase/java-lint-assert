package com.lint._assert.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class TestClassFinder {

    private final static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    /**
     * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
     */
    public static List<File> getClasses(String packageName) throws IOException {

        assert classLoader != null;

        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }

        ArrayList<File> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    public static String buildClassFilePath(String path) {
        String classPath = path.replace('\\', '/');

        int index = classPath.indexOf("/com/");
        if (index == -1)
            index = classPath.indexOf("/org/");
        classPath = classPath.substring(index);

        return classPath;
    }

    /**
     * Recurse to find all classes in a given directory and subdirs.
     */
    protected static List<File> findClasses(File directory, String packageName){
        final List<File> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        if (files == null)
            return classes;

        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(file);
            }
        }
        return classes;
    }

}
