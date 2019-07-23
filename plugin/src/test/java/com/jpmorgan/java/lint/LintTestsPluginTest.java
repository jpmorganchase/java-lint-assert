package com.jpmorgan.java.lint;

import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Assertions;


public class LintTestsPluginTest {

        @org.junit.jupiter.api.Test
        public void extendTestTaskWithLintAssert() {
            Project project = ProjectBuilder.builder().build();
            project.getPlugins().apply(JavaPlugin.class);
            project.getExtensions().create("lintPlugin", LintTestsPlugin.class);
            LintTestsPlugin plugin = (LintTestsPlugin) project.getExtensions().findByName("lintPlugin");
            Assertions.assertNotNull(plugin);

            plugin.apply(project); //and hope it doesn't crash
        }
}

