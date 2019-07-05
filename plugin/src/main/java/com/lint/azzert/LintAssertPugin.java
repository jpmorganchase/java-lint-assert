package com.lint.azzert;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.impldep.org.jetbrains.annotations.NotNull;

public class LintAssertPugin implements Plugin<Project> {

    @Override
    public void apply(@NotNull Project project) {
        project.getTasks().create("lint-assert", LintAssert.class, task -> task.setMessage("Hello") );
    }
}
