package com.lint.azzert;

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

        SourceSetContainer container = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
        Set<File> testDir = container.getByName("test").getRuntimeClasspath().getFiles();
        Set<URL> urls = new HashSet<>();
        for (File f : testDir) {
            URL url = null;
            try {
                url = f.toURI().toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            urls.add(url);
        }

        final URLClassLoader urlClassLoader = new URLClassLoader(urls.stream().toArray(URL[]::new), null);

        project.getTasks().withType(Test.class).forEach(task -> {
            LintTests taskExtension = task.getExtensions().create("lint", LintTests.class);

            task.doLast(task1 ->
            {
                try {
                    taskExtension.setClassLoader(urlClassLoader);
                    taskExtension.setVerbose(true);
                    String result = taskExtension.lint();
                    log.info(result);
                } catch (Exception e) {
                    log.error("Failed to lint", e);
                }
            });

        });
    }
}