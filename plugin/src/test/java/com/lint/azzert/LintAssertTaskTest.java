package com.lint.azzert;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LintAssertTaskTest {

    @Test
    void lint_azzert() {
        Project project = ProjectBuilder.builder().build();
        LintAssertTask task = project.getTasks().create("lint-asserts", LintAssertTask.class);
        Assertions.assertNotNull(task);

        task.setPackageName("com.lint");
        task.setClasspath("C:/git/java-lint-assert/client/build/classes/java/test");
        task.execute();
    }
}