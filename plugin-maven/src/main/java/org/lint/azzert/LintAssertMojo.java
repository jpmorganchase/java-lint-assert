package org.lint.azzert;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/*
Test stand-alone:
mvn install lint-assert:lint-assert
 */
@Mojo(name = "lint-assert")
public class LintAssertMojo extends AbstractMojo {

    @Parameter( property = "packageName")
    private String packageName;

    @Parameter( property = "includeClasspathJars", defaultValue = "false")
    private boolean includeClasspathJars;

    @Parameter( property = "verbose", defaultValue = "false")
    private boolean verbose;

    public void execute() {
        getLog().info("Hello from LintAssertMojo!;;" + this.toString());
        getLog().info(this.toString());
    }

    @Override
    public String toString() {
        return "LintAssertMojo{" +
                "packageName='" + packageName + '\'' +
                ", includeClasspathJars=" + includeClasspathJars +
                ", verbose=" + verbose +
                '}';
    }
}
