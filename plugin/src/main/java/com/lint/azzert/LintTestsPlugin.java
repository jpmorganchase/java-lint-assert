package com.lint.azzert;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.testing.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintTestsPlugin implements Plugin<Project> {

    final Logger log = LoggerFactory.getLogger(LintTestsPlugin.class);

    public void apply(final Project project) {

        project.getTasks().withType(Test.class).forEach(task -> {
            LintTests taskExtension = task.getExtensions().create("lint", LintTests.class);

            task.doLast(task1 ->
            {
                try {
                    String result = taskExtension.lint();
                    log.info(result);
                } catch (Exception e) {
                    log.error("Failed to lint", e);
                }
            });

        });
    }

}