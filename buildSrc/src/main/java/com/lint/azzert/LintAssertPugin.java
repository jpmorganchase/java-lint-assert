package com.lint.azzert;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class LintAssertPugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("hello", LintAssert.class, task -> task.setMessage("Hello") );
    }
}
