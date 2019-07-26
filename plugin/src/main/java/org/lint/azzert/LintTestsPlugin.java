package org.lint.azzert;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.testing.Test;
import org.lint.azzert.strategy.ToStringOutputStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

public class LintTestsPlugin implements Plugin<Project> {

    public static final String EXTENSION_NAME = "lintAssert";

    private final Logger log = LoggerFactory.getLogger(LintTestsPlugin.class);

    public void apply(final Project project) {

        final URLClassLoader urlClassLoader = getUrlClassLoader(
                project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets());

        project.getTasks().withType(Test.class).forEach(task -> {
            LintTests taskExtension = task.getExtensions().create(EXTENSION_NAME, LintTests.class);
            taskExtension.setClassLoader(urlClassLoader);

            task.doLast(task1 ->
            {
                try {
                    String result = new ToStringOutputStrategy(taskExtension.lintAssert()).render();
                    log.info(result);
                } catch (Exception e) {
                    log.error("Failed to lint", e);
                }
            });

        });
    }

    URLClassLoader getUrlClassLoader(SourceSetContainer container) {
        final Set<File> testDir = container.getByName("test").getRuntimeClasspath().getFiles();

        return new URLClassLoader(
                testDir.stream()
                        .map(f -> f.toURI())
                        .map(u -> {
                                    try {
                                        return u.toURL();
                                    } catch (MalformedURLException ignore) {
                                        return "";
                                    }
                                }
                        ).toArray(URL[]::new), null);
    }

}