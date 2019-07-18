package com.jpmorgan.java.lint;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.testing.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class LintTestsPlugin implements Plugin<Project> {

    final Logger log = LoggerFactory.getLogger(LintTestsPlugin.class);

    public void apply(final Project project) {

        final SourceSetContainer container = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
        final Set<File> testDir = container.getByName("test").getRuntimeClasspath().getFiles();
        final Set<URL> urls = filesToUrls(testDir);

        final URLClassLoader urlClassLoader = new URLClassLoader(urls.stream().toArray(URL[]::new), null);

        project.getTasks().withType(Test.class).forEach(task -> {
            LintTests taskExtension = task.getExtensions().create("lint", LintTests.class);

            task.doLast(task1 ->
            {
                try {
                    //Fixme - move outside doLast
                    taskExtension.setClassLoader(urlClassLoader);
                    taskExtension.setVerbose(true); //fixme - drop
                    String result = taskExtension.lintAssert();
                    log.info(result);
                } catch (Exception e) {
                    log.error("Failed to lint", e);
                }
            });

        });
    }

    private Set<URL> filesToUrls(Set<File> testDir) {
        Set<URL> urls = new HashSet<>();
        for (File f : testDir) {
            URL url = null;
            try {
                url = f.toURI().toURL();
            } catch (MalformedURLException e) {
                log.warn("Failed to convert the file "+ f +"to url " + e.getMessage());
            }
            urls.add(url);
        }
        return urls;
    }
}